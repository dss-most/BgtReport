<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="false" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<div class="container">
	<div class="row">
		<div class="span12">
			<label>กรุณาเลือกปีงบประมาณ : </label>
				<select name="fiscalyear" id="fiscalyearSlt" class="span2">
							<option>2553</option>
							<option>2554</option>
							<option>2555</option>
							<option>2556</option>
							<option>2557</option>
							<option>2558</option>
							<option>2559</option>
							<option selected="selected">2560</option>
				</select> 
			<select name="reportSlt" id="reportSlt" class="span6">
				<option value="noReport">กรุณาเลื่อกรายงาน...</option>
				<option value="findBGTSubmitNoVoucherReport">รายการสลิป BGT ที่ส่งเบิกแล้วแต่ยังไม่มีเลขที่งบประมาณรับ</option>
				<option value="findBGTVoucherNoDGAReport">รายการสลิป BGT ที่มีเลขงบประมาณรับแล้วแต่ยังไม่มีเลขที่ฎีกา</option>
			</select>
		</div>
	</div>
	<div class="row"> 
		<div class="span12"  id="resultTable">
			<div style="height: 400px;"></div>
		</div>
	</div>
</div>

<script id="tablePageTemplate" type="text/x-handlebars-template">
<div class="row">
	<div class="span2" style="padding-top: 30px;">
		พบรายการทั้งสิ้น {{totalElements}} รายการ
	</div>
	<div class="span10">
{{#unless isOnePage}} 	
	<div class="pagination">
		<ul>
			{{#if firstSkipIndex}}
				<li><a href="#" id=loadPageLink_{{firstSkipIndex}}>«</a></li>
			{{/if}}
			{{#each this.out}}
				<li class="{{cssClass}}"><a href="#" id="loadPageLink_{{index}}">{{index}}</a></li>
			{{/each}}
			{{#if lastSkipIndex}}
				<li><a href="#" id=loadPageLink_{{lastSkipIndex}}>»</a></li>
			{{/if}}
		</ul>
	</div>
{{/unless}}
	</div>
</div>
</script>
<script id="tableBGTVoucherNoDGATemplate" type="text/x-handlebars-template">
  <table class="table table-bordered">
    <thead>
	  <th width="70">โครงการ</th>
      <th width="140">เลขที่ BGT</th>
	  <th width="100">เลขที่ งปม.รับ</th>
      <th>รายละเอียด</th>
      <th width="100">จำนวนเงิน</th>
    </thead>
    <tbody>
      {{#each this}}
        <tr>
		  <td><strong>{{subProjectAbbr}}</strong> <br/>({{owner.abbr}})</td>
          <td>{{bgtCode}}<br/>{{formatShortDateBuddhist createdDate}} ({{createdByUser.loginName}})</td>
		  <td>{{voucherNumber}} <br/> {{formatShortDateBuddhist voucherDate}}</td>
          <td>{{subject}}<br/><span class="label label-info">รายละเอียด</span><br/><ol>{{{moreDescription}}}</ol></td>
          <td style="text-align: right;">{{formatDecimal paidAmount}}</td>
        </tr>
      {{/each}}
    </tbody>
  </table>
</script>
<script id="tableBGTSubmitNoVoucherTemplate" type="text/x-handlebars-template">
  <table class="table table-bordered">
    <thead>
	  <th width="70">โครงการ</th>
      <th width="140">เลขที่ BGT</th>
      <th>รายละเอียด</th>
      <th width="100">จำนวนเงิน</th>
    </thead>
    <tbody>
      {{#each this}}
        <tr>
		  <td><strong>{{subProjectAbbr}}</strong> <br/>({{owner.abbr}})</td>
          <td>{{bgtCode}}<br/>{{formatShortDateBuddhist createdDate}} ({{createdByUser.loginName}})</td>
          <td>{{subject}}<br/><span class="label label-info">รายละเอียด</span><br/><ol>{{{moreDescription}}}</ol></td>
          <td style="text-align: right;">{{formatDecimal paidAmount}}</td>
        </tr>
      {{/each}}
    </tbody>
  </table>
</script>


<script>
var buc;
var ResultView;
var resultView;
var appView;

var appView = Backbone.View.extend({

	el: $("body"),
	events: {
		"change #reportSlt": "selectReport",
		"change #fiscalyearSlt": "selectReport",
		"click a[id*=loadPageLink_]" : "loadPage"
	},
	
	render: function() {
		//nothing here
	},
	
	selectReport: function(e) {
		report = $("#reportSlt").val();
		if(report != "noReport") {
			this[report]();	
		}
				
	},
	
	findBGTVoucherNoDGAReport: function(e) {
		resultView.templateTable = Handlebars.compile($("#tableBGTVoucherNoDGATemplate").html());
		var fiscalYear = $('#fiscalyearSlt').val();
		buc.url = myUrl('BudgetUsages/'+fiscalYear+'/ALL_SUBPROJECT/findVoucherNumberNoDGA');
		buc.fetch();
	},
	
	findBGTSubmitNoVoucherReport : function(e) {
		resultView.templateTable = Handlebars.compile($("#tableBGTSubmitNoVoucherTemplate").html());
		var fiscalYear = $('#fiscalyearSlt').val();
		buc.url = myUrl('BudgetUsages/'+fiscalYear+'/ALL_SUBPROJECT/findSubmitNoVoucherNumber');
		buc.fetch();
	},
	
	loadPage: function(e) {
		var targetId = e.target.id;
		//console.log(targetId);
		var pageClick = targetId.split('_')[1];
		
		resultView.loadPage(pageClick-1);
	}
	
});


$(document).ready(function () {
	appView = new appView();
	
	buc = new BudgetUsageCollection();
	
	ResultView = Backbone.View.extend({
		initialize : function() {
			_.bindAll(this, "render");
			// Once the collection is fetched re-render the view
			this.collection.bind("reset", this.render);
		},
		
        el : "#resultTable",
        templateReportTable : null, 
        templatePage : Handlebars.compile($("#tablePageTemplate").html()),
       
		render: function() {
			//console.log("render");
			var json = this.collection.toJSON();
			var html = this.templateTable(json);
			
			$(this.el).html(this.templatePage(this.collection.page.outputJSON()));
			$(this.el).append(html);			
			
		},
		
		loadPage: function(pageIndex) {
			this.collection.fetch({data:{index: pageIndex}});
		}
    });
	
	resultView = new ResultView({collection: buc});
});
</script>


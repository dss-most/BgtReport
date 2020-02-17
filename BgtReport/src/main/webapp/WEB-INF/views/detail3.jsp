<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="false" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<div class="container">
	<div class="row">
		<div class="span12">
			<label>ปีงบประมาณ : </label>
				<select name="fiscalyear" id="fiscalyearSlt" class="span2">
							<option value="0">กรุณาเลือกปีงบประมาณ</option>
							<c:forEach var="year" items="${fiscalYears}">
	         						<option value="${year}">${year}</option>
	      					 </c:forEach>
				</select> 
				<select name="organizationSlt" id="organizationSlt" class="span2">
					
				</select>
			<select name="subProjectSlt" id="subProjectSlt" class="span6">
			</select>
		</div>
	</div>
	<div class="row"> 
		<div class="span12"  id="prcResultTable">
			<div></div>
			<div style="display: none;"></div>
		</div>
		<div class="span12"  id="buc1ResultTable">
			<div></div>
			<div style="display: none;"></div>
		</div>
		<div class="span12" id="buc2ResultTable">
			<div></div>
			<div style="display: none;"></div>
		</div>
		<div class="span12" id="buc3ResultTable">
			<div></div>
			<div style="display: none;"></div>
		</div>
	</div>
</div>

<script id="organizationSltTemplate" type="text/x-handlebars-template">
	<option selected="selected">เลือกสำนัก/โครงการ...</option>
	{{#each this}}
			<option value="{{id}}">{{abbr}}</option>
	{{/each}}
</script>

<script id="subProjectSltTemplate" type="text/x-handlebars-template">
<option selected="selected">กรุณาเลือกโครงการ...</option>
	{{#each this}}
		<option value="{{abbr}}"><strong>{{abbr}}</strong> - {{name}}</option>
	{{/each}}
</option>
</script>

<script id="tablePageTemplate" type="text/x-handlebars-template">
<div class="row">
	<div class="span10">
{{#unless isOnePage}} 	
	<div class="pagination">
		<ul>
			{{#if firstSkipIndex}}
				<li><a href="#" data-id={{firstSkipIndex}}>«</a></li>
			{{/if}}
			{{#each this.out}}
				<li class="{{cssClass}} loadPageCls"><a href="#" data-id="{{index}}">{{index}}</a></li>
			{{/each}}
			{{#if lastSkipIndex}}
				<li><a href="#" data-id={{lastSkipIndex}}>»</a></li>
			{{/if}}
		</ul>
	</div>
{{/unless}}
	</div>
</div>
</script>
<script id="tablePCMTemplate" type="text/x-handlebars-template">
  <table class="table table-bordered">
    <thead>
	  <th width="70">โครงการ</th>
      <th width="140">เลขที่ PCM</th>
	  <th>รายละเอียด</th>
	  <th width="100">ประมาณการ</th>
    </thead>
    <tbody>
      {{#each this}}
        <tr>
		  <td><strong>{{subProjectAbbr}}</strong> <br/>({{owner.abbr}})</td>
          <td>{{pcmNumber}}<br/>{{formatShortDateBuddhist createdDate}}<br/>
		  </td>
		  <td>{{{subject}}}<br/><span class="label label-info">รายละเอียด</span><br/>
				<ol>
					{{#each purchaseLineItems}}
						{{#if this}}
						<li>{{description}} จำนวน {{totalUnit}} {{uom}} @ {{formatDecimal unitPrice}} บาท รวมเป็นเงิน {{formatDecimal totalBudget}} บาท</li>
						{{/if}}
					{{/each}}
				</ol>
			  <span class="label label-important">สถานะ</span> {{status.state}} {{#if assignedToName}} -- {{/if}}{{assignedToName}}
		  </td>	
          <td style="text-align: right;">{{formatDecimal totalBudget}}</td>
        </tr>
      {{/each}}
    </tbody>
  </table>
</script>
<script id="tableBGTTemplate" type="text/x-handlebars-template">
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
var buc1, buc2;
var ResultView;
var appView;
var subProjectSltView;
var bucResultView;
var prcResultView;

var appView = Backbone.View.extend({

	el: $("body"),
	events: {
		"change #subProjectSlt": "fillSubProjectTable",
		"change #fiscalyearSlt": "fillOrganizationSlt",
		"change #organizationSlt": "fillSubProjectSlt",
		"click a.loadPageCls" : "loadPage"
	},
	
	render: function() {
		//nothing here
	},
	
	fillSubProjectTable : function(e) {
		var fiscalYear = $('#fiscalyearSlt').val();
		var subProjectAbbr = $("#subProjectSlt").val();
		
		prc.url = myUrl("PurchaseRequests/" + fiscalYear + "/" + subProjectAbbr.replace("/", "__") + "/findNotYetApproveOrBudgetUsage");
		prc.fetch();
		
		buc1.url = myUrl("BudgetUsages/" + fiscalYear + "/" + subProjectAbbr.replace("/", "__") + "/findSubmitNoVoucherNumber");
		buc1.fetch();
		
		buc2.url = myUrl("BudgetUsages/" + fiscalYear + "/" + subProjectAbbr.replace("/", "__") + "/findVoucherNumberNoDGA");
		buc2.fetch();
		
		buc3.url = myUrl("BudgetUsages/" + fiscalYear + "/" + subProjectAbbr.replace("/", "__") + "/findVoucherNumberDGA");
		buc3.fetch();

	},
	emptyResult : function() {
		prcResultView.emptyPage();
		prcResultView.secondDiv.hide();
		
		buc1ResultView.emptyPage();
		buc1ResultView.secondDiv.hide();
		
		buc2ResultView.emptyPage();
		buc2ResultView.secondDiv.hide();
		
		buc3ResultView.emptyPage();
		buc3ResultView.secondDiv.hide();
		
	},
	fillOrganizationSlt : function(e) {
		this.emptyResult();
		subProjectSltView.empty();
		
		
		var fiscalYear = $("#fiscalyearSlt").val();
		if(fiscalYear != 0) {
			oc.url = myUrl("SubProjects/listAllOrganization/" + fiscalYear);
			oc.fetch();
		} else {
			oc.reset();
		}
	},
	
	fillSubProjectSlt: function(e) {
		this.emptyResult();
		
		
		var organizationId = $("#organizationSlt").val();
		var fiscalYear = $("#fiscalyearSlt").val();
		spc.url = myUrl("SubProjects/" + fiscalYear + "/" + organizationId);
		spc.fetch();
	},
	
	loadPage: function(e) {
		//var targetId = e.target.id;
		//console.log(targetId);
		//var pageClick = targetId.split('_')[1];
		
		e.preventDefault();
		var pageClick = $(e.target).attr('data-id');
		//console.log("Loading Page : " + pageClick);
		
		resultView.loadPage(pageClick-1);
	}
	
});


$(document).ready(function () {
	appView = new appView();
	
	buc1 = new BudgetUsageCollection();
	buc2 = new BudgetUsageCollection();
	buc3 = new BudgetUsageCollection();
	prc = new PurchaseRequestCollection();
	spc = new SubProjectCollection();
	oc = new OrganizationCollection();
	
	OrganizationSltView = Backbone.View.extend({
		initialize : function() {
			_.bindAll(this, "render");
			// Once the collection is fetched re-render the view
			this.collection.bind("reset", this.render);
		},
		
		template: Handlebars.compile($("#organizationSltTemplate").html()),
		el: "#organizationSlt",
		collection : oc,
		render: function() {
			//console.log("rendering organizationSltView");
			//console.log(this.collection.toJSON());
			$(this.el).html(this.template(this.collection.toJSON()));
		}
	});
	
	
	SubProjectSltView = Backbone.View.extend({
		initialize : function() {
			_.bindAll(this, "render");
			// Once the collection is fetched re-render the view
			this.collection.bind("reset", this.render);
		},
		
		template: Handlebars.compile($("#subProjectSltTemplate").html()),
		el: "#subProjectSlt",
		collection : spc,
		empty: function() {
			$(this.el).empty();
		},
		render: function() {
			//console.log("rendering subProjecSltView");
			//console.log(this.collection.toJSON());
			$(this.el).html(this.template(this.collection.toJSON()));
		}
	});
	
	ResultView = Backbone.View.extend({
		initialize : function() {
			_.bindAll(this, "render");
			// Once the collection is fetched re-render the view
			this.collection.bind("reset", this.render);
			
	        this.firstDiv = $(this.$el.children()[0]);
	        this.secondDiv = $(this.$el.children()[1]);
	        this.rightIcon = "<i class='icon icon-chevron-right' style='padding-top:4px;'></i> ";
	        
	        
		},
		events: {
			"click a.firstDivCls" : "toggleFirstDiv",
			"click li.loadPageCls" : "loadPage"
			
		},
		
		loadPage: function(e) {
			e.preventDefault();
			var pageClick = $(e.target).attr('data-id');
			//console.log("Loading Page : " + (pageClick-1));
			
			this.collection.fetch({
				data: {
					index: pageClick-1
				}
			});
		},

		toggleFirstDiv : function(e) {
			this.firstDiv.find('i').toggleClass("icon-chevron-right icon-chevron-down");
			this.secondDiv.toggle();	
		},
		
        templatePage : Handlebars.compile($("#tablePageTemplate").html()),

        render: function() {
			//console.log("render");
			var json = this.collection.toJSON();
			
			//console.log(json);
			var html = this.templateTable(json);
			
			var totalElements = this.collection.page.get('totalElements');
			
			if(this.notCount) {
				this.firstDiv.html("<h4><a href='#' class='firstDivCls'>"+ this.rightIcon + this.header +  "</a></h4>");
				// this.secondDiv.hide();
				var json = this.collection.page.outputJSON();
				json.isOnePage = true;
				this.secondDiv.html(this.templatePage(json));
				this.secondDiv.append(html);
			} else {
				this.firstDiv.html("<h4><a href='#' class='firstDivCls'>"+ this.rightIcon + this.header + " (" + totalElements +  ") รายการ   </a></h4>");
				
				
				
				//this.secondDiv.hide();
				this.secondDiv.html(this.templatePage(this.collection.page.outputJSON()));
				this.secondDiv.append(html);
			}
		},
		emptyPage: function() {
			this.firstDiv.empty();
			if(this.secondDiv != null) {
				this.secondDiv.empty();	
			}
			
		},
		
		setOptions: function(options) {
			//console.log(options)
			this.resultTemplate = options.resultTemplate;
			this.header = options.header;
	        this.templateTable = Handlebars.compile($(this.resultTemplate).html());
	        if(options.notCount != null) {
	        	this.notCount = options.notCount;
	        } else {
	        	this.notCount = false;
	        }
		}
		
	});
	
	subProjectSltView = new SubProjectSltView();
	organizationSltView = new OrganizationSltView();
	
	prcResultView = new ResultView({
		el: "#prcResultTable",
		collection: prc
	});
	prcResultView.setOptions({
		notCount: true,
		header:"รายการ PCM ที่ยังไม่ได้ส่งเบิก",
		resultTemplate: "#tablePCMTemplate"
	});
	
	buc1ResultView = new ResultView({
		el: "#buc1ResultTable", 
		collection: buc1
	});
	buc1ResultView.setOptions({
		header:"รายการ BGT ส่งเบิกแล้วแต่ยังไม่มีเลขที่งบประมาณรับ",
		resultTemplate: "#tableBGTTemplate"
	});
	
	buc2ResultView = new ResultView({
		el: "#buc2ResultTable", 
		collection: buc2
	});
	buc2ResultView.setOptions({
		header:"รายการ BGT งบประมาณรับแล้วแต่ยังไม่มีเลขที่ฎีกา",
		resultTemplate: "#tableBGTTemplate"
	});
	
	
	buc3ResultView = new ResultView({
		el: "#buc3ResultTable", 
		collection: buc3
	});
	buc3ResultView.setOptions({
		header:"รายการ BGT ที่มีเลขที่ฎีกา",
		resultTemplate: "#tableBGTTemplate"
	});
});
</script>


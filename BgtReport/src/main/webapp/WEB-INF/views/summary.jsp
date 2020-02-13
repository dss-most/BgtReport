<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="false" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>


<div class="container">
	<div class="row">
		<div class="span12" style="text-align: right">
			ข้อมูล ณ วันที่ ${now}
		</div>
		<div class="span12">		
			<form class="well">
				<h3>สรุปการใช้จ่ายงบประมาณ</h3> 
				
				<div>
					<div class="pull-left">
						<label>กรุณาเลือกปีงบประมาณ : </label>
						<select name="fiscalyear" id="fiscalyear">
							<option>2553</option>
							<option>2554</option>
							<option>2555</option>
							<option>2556</option>
							<option>2557</option>
							<option>2558</option>
							<option>2559</option>
							<option>2560</option>
							<option>2561</option>
							<option>2562</option>
							<option selected="selected">2563</option>
						</select>
					</div>
					<div class="pull-left">
						<label>กรุณาเลือกหมวดงบประมาณ : </label>
						<select name="budgetTypeSlt" id="budgetTypeSlt">
							<option value="1">งบบุคลากร</option>
							<option value="2">งบดำเนินงาน</option>
							<option value="3">งบลงทุน</option>
							<option value="4">งบอุดหนุน</option>
							<option value="5">งบรายจ่ายอื่น</option>
							
						</select>
					</div>
				</div>
				<div class="clearfix"></div>
			</form>
			
			<div> 
				<div id="table">
				</div>
			</div>
			
		</div>
	</div>
	<div class="row" id="loading" style="display: none;"> Loading Report <img src="<c:url value='/resources/images/loading3.gif'/>"/> </div>
</div>
<script id="tableTemplate" type="text/x-handlebars-template">
<table class="table table-bordered table-striped budget">
	<thead>
		<tr>
			<td style="width: 500px;">หน่วยงาน/โครงการ</td>
			<td>ได้รับ (บาท) </td>
			<td>เบิกจ่ายแล้ว (บาท)</td>
			<td>คงเหลือ (บาท)</td>
			<td>ร้อยละการเบิกจ่าย</td>
		</tr>
	</thead>
	<tbody>
		<tr id="topRow">
			<td style='padding-top: 15px; text-align:center; border-bottom: 2px darkSeaGreen solid;'>
				 <b>{{top.orgAbbr}}</b></td>
			<td class="rightAlignNumber" style='padding-top: 15px; border-bottom: 2px darkSeaGreen solid;'><b>{{formatDecimal top.budgetAllocated}}</b></td>
			<td class="rightAlignNumber" style='padding-top: 15px; border-bottom: 2px darkSeaGreen solid;'><b>{{formatDecimalSum top.budgetApproved top.budgetUsed}}</b></td>
			<td class="rightAlignNumber" style='padding-top: 15px; border-bottom: 2px darkSeaGreen solid;'><b>{{allocMinusUsed top}}</b></td>
			<td class="rightAlignNumber" style='padding-top: 15px; border-bottom: 2px darkSeaGreen solid;'><b>{{formatDecimal top.percentUsed}}</b></td>
		</tr>
		{{#each this}}
		<tr class="sumRow" id="keywordRow_{{orgAbbr}}">
			<td style='padding-top: 15px; border-bottom: 2px darkSeaGreen solid;'>
				<a href="#" class="chevron"><i id="keywordChevron_{{id}}" class="icon-chevron-right"></i> {{orgAbbr}}</a></td>
			<td class="rightAlignNumber" style='padding-top: 15px; border-bottom: 2px darkSeaGreen solid;'>{{formatDecimal budgetAllocated}}</td>
			<td class="rightAlignNumber" style='padding-top: 15px; border-bottom: 2px darkSeaGreen solid;'>{{formatDecimalSum budgetApproved budgetUsed}}</td>
			<td class="rightAlignNumber" style='padding-top: 15px; border-bottom: 2px darkSeaGreen solid;'>{{allocMinusUsed this}}</td>
			<td class="rightAlignNumber" style='padding-top: 15px; border-bottom: 2px darkSeaGreen solid;'>{{formatDecimal percentUsed}}</td>
		</tr>
		
			{{#each subProjectCollection}}
				<tr style="display:none">
					<td style='padding-left: 40px''>
						{{abbr}} {{name}}
					</td>	
					<td class="rightAlignNumber">{{formatDecimal budgetAllocated}}</td>
					<td class="rightAlignNumber">{{formatDecimalSum budgetApproved budgetUsed}}</td>
					<td class="rightAlignNumber">{{allocMinusUsed this}}</td>
					<td class="rightAlignNumber">{{formatDecimal percentUsed}}</td>
				</tr>
				{{#each budgetAllocationItems}}
				<tr style="display:none">
					<td style='padding-left: 80px''>
						{{description}} {{qty}} {{unit}} {{#if pcmNumber}} <br/>   -  {{pcmNumber}} วันที่ {{pcmCreatedDate}} {{/if}}
					</td>	
					<td class="rightAlignNumber"></td>
					<td class="rightAlignNumber"></td>
					<td class="rightAlignNumber"></td>
					<td class="rightAlignNumber"></td>
				</tr>
				{{/each}}
			{{/each}}
		{{/each}}
	<tbody>
</table>
</script>
<script type="text/javascript">
var listView;
var e1;
var allAllocated = 0.0;
var allUsed = 0.0;
var allSubProjectBudget = new SubProjectBudget();
var subProjectBudgetCollection = new SubProjectBudgetCollection();
var orgBudgetCollection = new SubProjectBudgetCollection();

Handlebars.registerHelper("allocMinusUsed", function(row) {
	var budgetAllocated = row.budgetAllocated;
	var budgetApproved = row.budgetApproved;
	if(row.budgetAllocated == null || isNaN(row.budgetAllocated)) budgetAllocated = 0;
	if(row.budgetApproved == null || isNaN(row.budgetApproved)) budgetApproved = 0;
	return $.formatNumber(budgetAllocated - budgetApproved, {format:"#,##0.00", locale:"us"});
});

Handlebars.registerHelper("formatDate", function(date) {
	var day = moment(date);
	return day.format("D/MM/") + (day.year()+543);
});

$(document).ready(function () {
	var ListView = Backbone.View.extend({
		template: Handlebars.compile($("#tableTemplate").html()),
		el: '#table',
		events: {
			"click a.chevron": "chevronClick",
		},
		chevronClick: function(e) {
			//toggle chevron
			$(e.target).find('i').toggleClass("icon-chevron-right icon-chevron-down");
			
			//now get this row 
			var thisRow = $(e.target).parents('tr');
			
			thisRow.nextUntil('tr[id*=keywordRow]').toggle();
			return false;
			
		},
	
		render: function() {
			var json = orgBudgetCollection.toJSON();
			json.top=allSubProjectBudget.toJSON();
			
			var html = this.template(json);
			
			this.$el.html(html);
		}
		
	});
	

	
	var SelectionView = Backbone.View.extend({
		el: 'form.well',
		
		initialize: function() {
			this.fiscalYear = 2556;
			this.budgetSelection = 2;
		},
		
		events: {
			"change #fiscalyear" : "changeSlt",
			"change #budgetTypeSlt" : "changeSlt"
		},
		
		changeSlt: function(e) {
			
			this.fiscalYear = $('#fiscalyear').val();
			this.budgetTypeId  = $('#budgetTypeSlt').val();
			
			
			allAllocated = 0.0;
			allUsed = 0.0;
			allApproved = 0.0;
			orgBudgetCollection.reset();
			orgBudgetCollection.comparator = function(sp) { return 100 - sp.get('percentUsed');};
			subProjectBudgetCollection.fetch({
				url: myUrl('SubProjectBudget/'+this.fiscalYear+'/' + this.budgetTypeId),
				success: function(response) {
					var orgCollection = subProjectBudgetCollection.groupBy(function(num){ return num.get('orgId'); });
					_.each(orgCollection, function(org) {
						var totalAllocated = 0.0;
						var totalUsed = 0.0;
						var totalApproved = 0.0;
						_.each(org, function(sp) {
							
							totalAllocated += sp.get('budgetAllocated');
							totalApproved += sp.get('budgetApproved');
							totalUsed += sp.get('budgetUsed');
							sp.set('percentUsed', 
									( (parseFloat(sp.get('budgetApproved')) + parseFloat(sp.get('budgetUsed'))) 
											/ parseFloat(sp.get('budgetAllocated')) ) * 100);
							
						});
						
						var orgSpb = new SubProjectBudget();
						orgSpb.set('budgetAllocated', totalAllocated);
						orgSpb.set('budgetUsed', totalUsed);
						orgSpb.set('budgetApproved', totalApproved);
						orgSpb.set('orgAbbr', org[0].get('orgAbbr'));
						
						orgSpb.set('percentUsed', ((parseFloat(totalApproved) + parseFloat(totalUsed))/parseFloat(totalAllocated))*100);
						
						
						var spb = new SubProjectBudgetCollection();
						spb.comparator = function(sp) { return 100 - sp.get('percentUsed');};
						_.each(org, function(sp) {
							if(sp.get('budgetAllocated') > 0 ) {
								spb.add(sp);
							}
						});
						
						orgSpb.set('subProjectCollection', spb.toJSON());
						
						allAllocated += totalAllocated;
						allUsed += totalUsed;
						allApproved += totalApproved;
						
						
						if(orgSpb.get('budgetAllocated') > 0) {
							orgBudgetCollection.add(orgSpb);
						}
						// now we'll sum all over orgBudget
						
					});
					
					allSubProjectBudget.set('orgAbbr', "รวมงปม.ทุกหน่วยงาน");
					allSubProjectBudget.set('budgetAllocated', allAllocated);
					allSubProjectBudget.set('budgetApproved', allApproved);
					allSubProjectBudget.set('budgetUsed', allUsed);
					allSubProjectBudget.set('percentUsed', ( (allApproved + allUsed) / allAllocated ) * 100.0 ) ;
					
					listView.render();	
				}
			});
		}
		
	});
	
	listView= new ListView();
	sltView= new SelectionView();

});


</script>





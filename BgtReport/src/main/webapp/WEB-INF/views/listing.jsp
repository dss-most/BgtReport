<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="false" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>


<div class="container">
	<div class="row">
		<div class="span12">
			<form class="well">
				<label>กรุณาเลือกปีงบประมาณ : </label>
				<select class="span4" name="fiscalYear" id="fiscalYear">
							<option>2553</option>
							<option>2554</option>
							<option>2555</option>
							<option>2556</option>
							<option>2557</option>
							<option>2558</option>
							<option>2559</option>
							<option selected="selected">2560</option>
				</select>
				<label>กรุณาเลือกงบรายจ่าย
				<span id="expenseNote" class="label label-important"
					title="ประเภทหมวดรายจ่าย" 
					data-content="(1) = งบบุคลากร<br/>(2) = งบดำเนินงาน<br/>(3) = งบลงทุน<br/>(4) = งบเงินอุดหนุน<br/>(5) = งบรายจ่ายอื่น<br/>">
					* หมายเหตุ</span></label>
				<select class="span4" name="parentExpenseTypeOption" id="parentExpenseTypeOption">
					
				</select>
				

				<br/>
				<a href="#" class="btn btn-primary" onclick="loadResultTable()">ค้นหา</a> 
				<span id="loading" style="display:none;">Loading Result <img src="<c:url value='/resources/images/loading3.gif'/>"/></span>
			</form>
			
			
		</div>
		
	</div>
	<div class="row" id="resultTable" style="display: none;"> 
		<div class="span12" style="padding-top:10px;">
			<span id="resultTableCaption"></span>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th style="text-align: center; vertical-align: middle;" width="12%"><b>หมายเลข BGT</b></th>
						<th style="text-align: center; vertical-align: middle;" width="9%"><b>โครงการ</b></th>
						<th style="text-align: center; vertical-align: middle;"><b>รายละเอียด</b></th>
						<th style="text-align: center; vertical-align: middle;" width="14%"><b>ร้านค้า/ผู้เบิก</b></th>
						<th style="text-align: center; vertical-align: middle;" width="7%"><b>จำนวนเงินที่เบิกจ่าย</b></th>
						<th style="text-align: center; vertical-align: middle;" width="9%"><b>สถานะการเบิกจ่าย</b></th>
					</tr>
				</thead>
				<tbody id="resultTableBody">
				</tbody>
			</table>
		</div> 
	</div>
</div>

<script type="text/javascript">
<!--

function loadResultTable() {
	$('#loading').show();
	$.ajax({
		url: 'api/json/findBGTByFiscalYearExpenseType',
		data: {
			fiscalYear: $('#fiscalYear').val(),
			parentExpenseTypeCode: $('#expenseTypeOption').val()
		},
		success: function(data) {
			var b = $('#resultTableBody');
			b.empty();
			$.each(data, function(index, value){
				var tr = $('<tr></tr>');
				var bgtCol = $('<td width="12%">'+value["หมายเลข BGT"]+'<br/>('+value["วันที่เบิกจ่าย"]+')</td>');
				tr.append(bgtCol);
				
				var sprojCol = $('<td style="text-align: center" width="9%"><a href="#" rel="tooltip" title="'+value["ชื่อโครงการ"]+'" >'+value["ชื่อย่อโครงการ"]+'</a></td>');
				
				tr.append(sprojCol);
				tr.append('<td>'+ value["ชื่อเรื่องใน BGT"] +'<br/><span class="label label-info">รายละเอียด</span><br/><ol>' + value["รายละเอียด"] + '</ol></td>');
				tr.append('<td width="14%">'+ value["ร้านค้า"] +'<ul>'+ value["รายละเอียดใบเสร็จรับเงิน"] +'</ul></td>');
				tr.append('<td style="text-align: right" width="7%">'+ $.formatNumber(value["งบประมาณเบิกจ่ายรวมใน BGT"], {format:"#,##0.00", locale:"us"}) +'</td>');
				var dgaTd = $('<td style="text-align: center" width="9%">'+ value["สถานะการเบิกจ่าย"] +'</td>');
				if(value["เลขที่ฎีกา"] != null) {
					dgaTd.append("<br/>ฎ."+value["เลขที่ฎีกา"] +" <br/> " + value["วันที่ฎีกา"] );
				}
				tr.append(dgaTd);
				b.append(tr);
			});
			
		}
	}).done(function (){
		$('#resultTableCaption').empty();
		$('#resultTableCaption').append("<h4>รายการ BGT ปีงปบระมาณ  "+$('#fiscalYear').val()+ " <br/>งบ " + $('#parentExpenseTypeOption option:selected').text() + " หมวดรายจ่าย"+ $('#expenseTypeOption option:selected').text() +"</h4>");
		
		$('#resultTable tbody td a').tooltip({placement:'right'});
		$('#resultTable tbody td a').click(function() {
			return false;
		});
		$('#resultTable').show();
		$('#loading').hide();
	});
}

function loadReport(url) {
	window.location=url+'?fiscalYear='+$('#fiscalyear').val();
}

function loadParentExpenseTypeOption(fiscalYear) {
	//empty old one first
	$('#parentExpenseTypeOption').empty();
	
	//populate bugetType Option
	$.ajax({
		url: 'api/json/findParentExpenseType',
		data: {
			fiscalYear: $('#fiscalYear').val()	
		},
		success: function(data) {
			$.each(data, function(index, value) {
				$('#parentExpenseTypeOption').append("<option value=" + value.P_EXPENSE_TYPE_CODE + ">" + value.P_EXPENSE_TYPE_NAME + "</option>");
			});
		}
	}).done(function() {
		loadExpenseTypeOption();
	});
}

function loadExpenseTypeOption(fiscalYear, parentExpenseTypeCode) {
	//empty old one first
	$('#expenseTypeOption').empty();
	
	//populate bugetType Option
	$.ajax({
		url: 'api/json/findExpenseType',
		data: {
			fiscalYear: $('#fiscalYear').val(),
			parentExpenseTypeCode: $('#parentExpenseTypeOption').val()
		},
		success: function(data) {
			$.each(data, function(index, value) {
				$('#expenseTypeOption').append("<option value=" + value.EXPENSE_TYPE_CODE + ">" + value.EXPENSE_TYPE_NAME + "</option>");
			});

		}
	});
}



$(document).ready(function () {
	loadParentExpenseTypeOption();
	
	$('#fiscalYear').change(function() {
		loadParentExpenseTypeOption();
	});
	
	$('#parentExpenseTypeOption').change(function() {
		loadExpenseTypeOption();
	});
	
	$('#expenseNote').popover('hide');
});
</script>





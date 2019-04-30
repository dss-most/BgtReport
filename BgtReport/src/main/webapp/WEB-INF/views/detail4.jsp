<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="false" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>


<div class="container">
	<div class="row">
		<div class="span12">
			<form class="well">
				<label>กรุณาเลือกปีงบประมาณ : </label>
				<select name="fiscalyear" id="fiscalyear">
							<option>2553</option>
							<option>2554</option>
							<option>2555</option>
							<option>2556</option>
							<option>2557</option>
							<option>2558</option>
							<option>2559</option>
							<option selected="selected">2560</option>
				</select>
				<label>กรุณาเลือกรายการ</label>
				<ol>
					<li><a href="#" onclick="loadReport('bgtAll.xls')">รายการ BGT ทั้งปีงบประมาณ</a></li>
					<li><a href="#" onclick="loadReport('bgtHwNoPcmList.xls')">เบิกจ่ายครุภัณฑ์ไม่มีวศ. 22</a></li>
					<li><a href="#" onclick="loadReport('bgtOtherNoPcm.xls')">เบิกจ่ายรายการอื่นไม่มีวศ. 22</a></li>
					<li><a href="#" onclick="loadReport('bgtHasPcm.xls')">เบิกจ่ายมีวศ. 22</a></li>
				</ol>
			</form>
			
			
		</div>
	</div>
	<div class="row" id="loading" style="display: none;"> Loading Report <img src="<c:url value='/resources/images/loading3.gif'/>"/> </div>
</div>

<script type="text/javascript">
<!--
function loadReport(url) {
	window.location=url+'?fiscalYear='+$('#fiscalyear').val();
}
</script>





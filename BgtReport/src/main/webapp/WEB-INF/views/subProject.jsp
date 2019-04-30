<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript" src="<c:url value='/resources/pages-js/subProject.js'/>"></script>

รายละเอียดการเบิกจ่าย โครงการ${subProject.name} (${subProject.abbr})

<table>
<c:forEach var="bu" items="${budgetUsageList}">
	<tr>
		<td>${bu.bgtCode}</td>
		<td>${bu.subject}</td>
		<td>${bu.description}</td>
		<td><fmt:formatNumber type="currency" value="${bu.approvedAmount}"/></td>
		<td><fmt:formatNumber type="currency" value="${bu.paidAmount}"/></td>
	</tr>
</c:forEach>
</table>
    
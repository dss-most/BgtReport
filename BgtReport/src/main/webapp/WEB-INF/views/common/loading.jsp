<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="th">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

	<link href="<c:url value='/resources/js/bootstrap-2.0.4/css/bootstrap.css'/>" rel="stylesheet" type="text/css"/>
	<link href="<c:url value='/resources/css/application.css'/>" rel="stylesheet" type="text/css"/>
	<script src="<c:url value='/resources/js/jquery/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
	
	<title>Loading Excel Report...</title>
	
	<script type="text/javascript">
// 	$('document').ready(function() {
// 		$('#myifrm').load(function() {
// 			alert('me');
// 		});
// 	});
	
	</script>
</head>

<body>
	<div id="load" >
		Loading Report <img src="<c:url value='/resources/images/loading3.gif'/>"/>
	</div>
	<div id="close" style="display:none;"><a href="#" class="btn btn-primary" onclick="window.close()">finish Loading close the window</a></div>
	<div id="ifrm" style="display:none;">
		<iframe id="myifrm" src="/" onload="alert('me')"> </iframe>
	</div>
	
</body>
</html>

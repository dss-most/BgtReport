<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="th">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

	<link href="<c:url value='/resources/js/bootstrap-2.1/css/bootstrap.css'/>" rel="stylesheet" type="text/css"/>
	<link href="<c:url value='/resources/css/application.css'/>" rel="stylesheet" type="text/css"/>
	
	<script type="text/javascript" src="<c:url value='/resources/js/jquery/jquery-1.7.2.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/jshashtable-2.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.numberformatter-1.2.3.js'/>"></script>	
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.dateFormat-1.0.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-2.1/js/bootstrap.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/underscore-1.3.3-min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/backbone-0.9.2-min.js'/>"></script>					
	<script type="text/javascript" src="<c:url value='/resources/js/handlebars-1.0.0.beta.6.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/moment-1.7.2.min.js'/>"></script>
	
	<!-- main app -->
	<script type="text/javascript" src="<c:url value='/resources/pages-js/backOffice-app.js'/>"></script>
	
	<title>${title}</title>
</head>

<body>
	
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="#">BGT Report</a>
				<div class="nav">
					<ul class="nav">
						<li class="${homeActive ? 'active' : '' }"><a href="<c:url value='/'/>">Home</a></li>
						<li class="${detail1Active ? 'active' : '' }"><a href="<c:url value='/detail1.html'/>">Detail1</a></li>
						<li class="${detail2Active ? 'active' :  '' }"><a href="<c:url value='/detail2.html'/>">Detail2</a></li>
						<li class="${detail3Active ? 'active' :  '' }"><a href="<c:url value='/detail3.html'/>">Detail3</a></li>
						<li class="${detail4Active ? 'active' :  '' }"><a href="<c:url value='/detail4.html'/>">Detail4</a></li>
						<li class="${contactActive ? 'active' :  '' }"><a href="#contact">Contact</a></li>
					</ul>
				</div>
				<div class="nav pull-right">
					<ul class="nav">
						<li><a href="#">Welcome: | Logout</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>

	<tiles:insertAttribute name="body"/>
			
     <footer>
      	<div id="footer">	
			<tiles:insertAttribute name="footer"/>
 		</div>
 	</footer>

</body>
</html>
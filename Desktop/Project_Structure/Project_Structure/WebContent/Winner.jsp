<!-- Author: Mounika G/9096544949/mounikag449@gmail.com
Created Date: 1/2/2017
Description: This is a welcome page which displays a form to accept number of users,random prize
             value which generates by Rest API and Instructions to play the game. -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/header.css">
<link rel="stylesheet" href="css/winner.css">
</head>
<body class="winnerWeb">
	<div id="main" class="page-main home-page" data-lang="en">
		<header>
		<div class="cnt-header-1" data-predictive-enabled="true"
			data-predictive-uri="/bin/servlet/abbott/predictivesearchresult"
			data-predictive-collection="lifetothefullest_abbott_en"
			data-predictive-front-end="lifetothefullest_abbott">
			<div class="external_links"></div>
			<nav class="topnav"></nav>

		</div>

		</header>
		<div class="container">
		  <div class="winnerpage">
		   <img class="imgdrop" src="Images/congrats.jpg" alt="Paris" width="300" height="300">
		   </div>
		   <div>
		    <span>Winner Is :<%=request.getParameter("winner")%></span> <br>
		    <span>Prize Value :<%=request.getParameter("prizeval")%></span> 
		  </div>
		</div>
	</div>
</body>
</html>
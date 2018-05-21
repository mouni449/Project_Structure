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
<link rel="stylesheet" href="css/form.css">
<link rel="stylesheet" href="css/popup.css">
<link rel="stylesheet" href="css/winner.css">
<script src="js/WinnerCalculation.js"></script>
<script src="js/Users.js"></script>
<style type="text/css">
</style>

<script type="text/javascript">
	
</script>

</head>
<body class="mstrbody" onload="addUsers()">
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
			<div id="popup">
				<ul style="display: block">
					<li><span class="dropdown-menu-title">Instructions</span>

						<li><span class="color-box" style="background-color: #00b140;"></span>
						<span class="dropdown-menu-item">Bet Value should not be greater than 10</span></li>
					<li><span class="color-box" style="background-color: #eeb33b;"></span>
						<span class="dropdown-menu-item">Each User get 3 chances to bet and should not be same numbers</span></li> 

				</ul>
			</div>
		
			
			<form class="form-size" action="Winner.jsp" name="firstbet"
							data-toggle="validator" id="firstbet">
							<h3 style="display: none">First Player</h3>
				<%
					String prize = request.getParameter("prize1");
				%>
				<input type="hidden" id="prizeval" name="prizeval" value=<%=prize%>>
				<%
					System.out.println("Prize :" + request.getParameter("prize1"));
				%>
				<input type="hidden" id="userno" name="userno"
								value=<%=request.getParameter("userno")%>>
				<input type="hidden" id="winner" name="winner" value="">
				
				<div class="form-group" style="display: none" id="fbet1">
					<label for="prize">Bet Value1:</label> <input type="text"
									class="form-control" id="fbetv1" placeholder="Enter Bet value"
									name="fbetv1" min="1" max="10"
									title="Value should not be greater than 10">
				</div>
				<div class="form-group" style="display: none" id="fbet2">
					<label for="prize">Bet Value2:</label> <input type="text"
									class="form-control" id="fbetv2" placeholder="Enter Bet value"
									name="fbetv2" min="1" max="10"
									title="Value should not be greater than 10">
				</div>
				<div class="form-group" style="display: none" id="fbet3">
					<label for="prize">Bet Value3:</label> <input type="text"
									class="form-control" id="fbetv3" placeholder="Enter Bet value"
									name="fbetv3" min="1" max="10"
									title="Value should not be greater than 10">
				</div>

				<h3 style="display: none">Second Player</h3>
				<div class="form-group" style="display: none" id="sbet1">
					<label for="prize">Bet Value1:</label> <input type="text"
									class="form-control" id="sbetv1" placeholder="Enter Bet value"
									name="sbetv1" min="1" max="10"
									title="Value should not be greater than 10">
				</div>
				<div class="form-group" style="display: none" id="sbet2">
					<label for="prize">Bet Value2:</label> <input type="text"
									class="form-control" id="sbetv2" placeholder="Enter Bet value"
									name="sbetv2" min="1" max="10"
									title="Value should not be greater than 10">
				</div>
				<div class="form-group" style="display: none" id="sbet3">
					<label for="prize">Bet Value3:</label> <input type="text"
									class="form-control" id="sbetv3" placeholder="Enter Bet value"
									name="sbetv3" min="1" max="10"
									title="Value should not be greater than 10">
				</div>

				<h3 style="display: none">Third Player</h3>
				<div class="form-group" style="display: none" id="tbet1">
					<label for="prize">Bet Value1:</label> <input type="text"
									class="form-control" id="tbetv1" placeholder="Enter Bet value"
									name="tbetv1" min="1" max="10"
									title="Value should not be greater than 10">
				</div>
				<div class="form-group" style="display: none" id="tbet2">
					<label for="prize">Bet Value2:</label> <input type="text"
									class="form-control" id="tbetv2" placeholder="Enter Bet value"
									name="tbetv2" min="1" max="10"
									title="Value should not be greater than 10">
				</div>
				<div class="form-group" style="display: none" id="tbet3">
					<label for="prize">Bet Value3:</label> <input type="text"
									class="form-control" id="tbetv3" placeholder="Enter Bet value"
									name="tbetv3" min="1" max="10"
									title="Value should not be greater than 10">
				</div>

				<button type="submit" class="btn btn-default"
								onclick="return Validate()">Submit</button> 
				
			</form>
			<button class="btn btn-default addbutton"
							onclick="addBetFirstPerson()" style="display: none"
							id="addnewbet1">Add New Bet Person1</button>
			<button class="btn btn-default addbutton"
							onclick="addBetSecondPerson()" style="display: none"
							id="addnewbet2">Add New Bet Person2</button>
			<button class="btn btn-default addbutton" onclick="addBet3Person()"
							style="display: none" id="addnewbet3">Add
				New Bet Person3</button>

		
					</div>


	</div>

</body>
</html>
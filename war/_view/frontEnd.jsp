<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="mainstyle.css">
	</head>

	<body>

		<div class="main">
			<img src="logo.gif.png" alt="YCP"></img>

			<div class="mainNav">
				<ul class="navBar">
  					<li><button type="submit" formmethod="post" name="login"</button></li>
				</ul>
			</div>

			<form>
  				<input type="text" name="search" placeholder="Search by title, author, ISBN...">
			</form>
		
			<div class="inlineImage">
				<img src="spartan.jpg" alt="Smarty McSpartan"></img>
			</div>
		</div>
</body>
</html>
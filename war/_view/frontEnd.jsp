<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="_view/mainstyle.css">
	</head>

	<body>
		<form action="${pageContext.servletContext.contextPath}/home" method="post">
			<div class="main">
				<img src="logo.gif.png" alt="YCP"></img>
			
				<div class="mainNav">
					<ul class="navBar">
  						<li><input name="buttonPress" type="submit" value="login" /input></li>
					</ul>
				</div>

				<form>
  					<input type="text" name="search" placeholder="Search by title, author, ISBN...">
				</form>
		
				<div class="inlineImage">
					<img src="spartan.jpg" alt="Smarty McSpartan"></img>
				</div>
			</div>
		</form>
	</body>
</html>
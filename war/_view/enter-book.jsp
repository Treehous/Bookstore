<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="_view/mainstyle.css">
	</head>

	<body>
		<table>
			<tr>
				<td>
					<div><img src="res/logo.gif.png" alt="YCP"></img></div>
					
					<div>
						<form action="${pageContext.servletContext.contextPath}/enter-book" method="post">
							<div> <input type="text" name="title" placeholder="Title"> </div>
							<div> <input type="text" name="author" placeholder="Author"></div>
							<div> <input type="text" name="isbn" placeholder="ISBN"></div>
							<div> <input type="submit" name="submit" value="enterBook"> </div>
						</form>
					</div>
				</td>
				
				<td>
					<form action="${pageContext.servletContext.contextPath}/create-login" method="post">
  						<input name="buttonPress" type="submit" value="login" />
					</form>		
				</td>
		</table>
		<c:if test="${! empty errorMessage}">
			<div>${errorMessage}</div>
		</c:if>
		
		<c:if test="${! empty successMessage}">
			<div>${successMessage}</div>
		</c:if>
		
		<div class="inlineImage">
			<img src="res/spartan.gif" alt="Smarty McSpartan"></img>
		</div>
		
		<div>
			<form action="${pageContext.servletContext.contextPath}/search" method="get">
				<input name="toSearch" type="submit" value="Search For A Book"/>
			</form>
		</div>
	</body>
</html>
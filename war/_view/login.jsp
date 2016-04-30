<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="_view/mainstyle.css">
	</head>


	<body>
		<div><img src="res/logo.gif.png" alt="YCP"></img></div>
					
		<div>
			<form action="${pageContext.servletContext.contextPath}/login" method="post">
			<table>
				<tr> <td>Username:</td>
				<c:if test="${! empty username}">
					<td><input type="text" name="username" value="${username}"></td>
				</c:if>
				
				<c:if test="${empty username}">
					<td><input type="text" name="username" placeholder="Username"></td>
				</c:if>
				</tr>
				
				<tr> <td> Password:</td>
				<c:if test="${! empty password}">
					<td><input type="text" name="password" value="${password}"></td>
				</c:if>
				
				<c:if test="${empty password}">
					<td><input type="text" name="password" placeholder="Password"></td>
				</c:if>
				</tr>
				
				<tr><td><input type="submit" name="buttonPress" value="Log-in"></td></tr>
			</table>
			
			<c:if test="${! empty errorMessage}">
				<tr>${errorMessage}</tr>
			</c:if>
			
			</form>
		</div>
	</body>
</html>
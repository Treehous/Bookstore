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
			<form action="${pageContext.servletContext.contextPath}/create-login" method="post">
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
					<td><input type="text" name="pass1" value="${password}"></td>
				</c:if>
				
				<c:if test="${empty password}">
					<td><input type="text" name="pass1" placeholder="Password"></td>
				</c:if>
				</tr>
				
				<tr> <td> Re-Enter:</td>
				<c:if test="${! empty pass2}">
					<td><input type="text" name="pass2" value="${pass2}"></td>
				</c:if>
				
				<c:if test="${empty pass2}">
					<td><input type="text" name="pass2" placeholder="Re-enter Password"></td>
				</c:if>
				</tr>

				<tr> <td> Name:</td>
				<c:if test="${! empty name}">
					<td><input type="text" name="name" value="${name}"></td>
				</c:if>
				
				<c:if test="${empty name}">
					<td><input type="text" name="name" placeholder="Name"></td>
				</c:if>
				</tr>
				
				<tr> <td> Email:</td>
				<c:if test="${! empty email}">
					<td><input type="text" name="email" value="${email}"></td>
				</c:if>
				
				<c:if test="${empty email}">
					<td><input type="text" name="email" placeholder="Email Address"></td>
				</c:if>
				</tr>
				
				<tr> <td> Phone Number:</td>
				<c:if test="${! empty phone}">
					<td><input type="text" name="phone" value="${phone}"></td>
				</c:if>
				
				<c:if test="${empty phone}">
					<td><input type="text" name="phone" placeholder="Phone Number"></td>
				</c:if>
				</tr>
				
				<tr><td><input type="submit" name="buttonPress" value="Create"></td></tr>
			</table>
			<c:if test="${! empty errorMessage}">
				<tr>${errorMessage}</tr>
			</c:if>
			</form>
		</div>
	</body>
</html>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Update Account</title>
		<link rel="stylesheet" type="text/css" href="_view/mainstyle.css">
	</head>


	<body>
		<form action="${pageContext.servletContext.contextPath}/home" method="get">
			<input type="image" src="res/logo.gif.png" alt="YCP Logo" name="buttonPress"/>
		</form>
					
		<div>
			<form action="${pageContext.servletContext.contextPath}/update-account" method="post">
			<table>
				<tr> <td>Username:</td>
				<c:if test="${! empty account.username}">
					<td><input type="text" name="username" value="${account.username}" disabled></td>
				</c:if>
				
				<c:if test="${empty account.username}">
					<td><input type="text" name="username" placeholder="Username" disabled></td>
				</c:if>
				</tr>
				
				<tr> <td> Password:</td>
				<c:if test="${! empty account.password}">
					<td><input type="text" name="pass1" value="${account.password}"></td>
				</c:if>
				
				<c:if test="${empty account.password}">
					<td><input type="text" name="pass1" placeholder="Password"></td>
				</c:if>
				</tr>
				
				<tr> <td> Re-Enter:</td>
					<td><input type="text" name="pass2" placeholder="Re-enter Password"></td>
				</tr>

				<tr> <td> Name:</td>
				<c:if test="${! empty account.name}">
					<td><input type="text" name="name" value="${account.name}"></td>
				</c:if>
				
				<c:if test="${empty account.name}">
					<td><input type="text" name="name" placeholder="Name"></td>
				</c:if>
				</tr>
				
				<tr> <td> Email:</td>
				<c:if test="${! empty account.email}">
					<td><input type="text" name="email" value="${account.email}"></td>
				</c:if>
				
				<c:if test="${empty account.email}">
					<td><input type="text" name="email" placeholder="Email Address"></td>
				</c:if>
				</tr>
				
				<tr> <td> Phone Number:</td>
				<c:if test="${! empty account.phoneNumber}">
					<td><input type="text" name="phone" value="${account.phoneNumber}"></td>
				</c:if>
				
				<c:if test="${empty account.phoneNumber}">
					<td><input type="text" name="phone" placeholder="Phone Number"></td>
				</c:if>
				</tr>
				
				<tr><td><input type="submit" name="buttonPress" value="Update"></td></tr>
			</table>
			<c:if test="${! empty errorMessage}">
				<tr>${errorMessage}</tr>
			</c:if>
			</form>
		</div>
	</body>
</html>
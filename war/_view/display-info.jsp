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
					<form action="${pageContext.servletContext.contextPath}/home" method="get">
						<input type="image" src="res/logo.gif.png" alt="YCP Logo" name="buttonPress"/>
					</form>
					
					<div>
					<c:if test="${! empty errorMessage}">
						<div>${errorMessage}</div>
					</c:if>
					
					<c:if test="${empty errorMessage}">
						<table>
						<tr>
							<td>Book Title: </td>
							<td> ${bfs.book.title}</td>
			    		</tr>
			    		<tr>
							<td>Book ISBN: </td>
							<td> ${bfs.book.isbn}</td>
			    		</tr>
			    		<tr>
							<td>Book Price: </td>
							<td> ${bfs.price}</td>
			    		</tr>
			    		<tr>
							<td>Book Owner: </td>
							<td> ${bfs.owner.name}</td>
			    		</tr>
			    		<tr>
							<td>Owner Email: </td>
							<td> ${bfs.owner.email}</td>
			    		</tr>
			    		<tr>
							<td>Owner Phone Number: </td>
							<td> ${bfs.owner.phoneNumber}</td>
			    		</tr>
			    		</table>
			    	</c:if>
					</div>
				</td>
				
				<td>
						<c:choose>
				<c:when test="${loggedin}">
					<div> 
						<table>
							<tr>
								<td>Username: ${account.username}</td>
							</tr>
							<tr>
								<td>Name: ${account.name}</td>
							</tr>
							<tr>
								<td>Email: ${account.email}</td>
							</tr>
							<tr>
								<td>Phone Number: ${account.phoneNumber}</td>
							</tr>
							<tr>
								<td>Number of Books for Sale: ${account.numberOfBooksForSale}</td>
							</tr>
							<tr>
								<table>
								<tr>
								<td>  
									<form action="${pageContext.servletContext.contextPath}/enter-book" method = "get">
										<input name="buttonPress" type="submit" value="Sell A Book">
									</form>
								</td>
								<td>
									<form action="${pageContext.servletContext.contextPath}/update-account" method = "get">
										<input name="buttonPress" type="submit" value="Edit Account">
									</form>
								</td>
								<td>
									<form action="${pageContext.servletContext.contextPath}/login" method = "post">
										<input name="buttonPress" type="submit" value="Logout">
									</form>
								</td>
								</tr>
								</table>
							</tr>
						</table>
					</div>
				</c:when>
				<c:otherwise>
					<form action="${pageContext.servletContext.contextPath}/create-login" method="get">
						<div>Username: <input name="Username" type="text" placeholder="Username"/> </div>
						<div>Password: <input name="Password" type="text" placeholder="Password"/> </div>
  						<div> 
  							<input name="buttonPress" type="submit" value="Login" />
  							<input name="buttonPress" type="submit" value="Create" />
  						</div>
					</form>
				</c:otherwise>
				</c:choose>	
				</td>
		</table>
		
		<c:if test="${! empty errorMessage}">
			<div>${errorMessage}</div>
		</c:if>
		
		<c:if test="${! empty successMessage}">
			<div>${successMessage}</div>
		</c:if>
	</body>
</html>
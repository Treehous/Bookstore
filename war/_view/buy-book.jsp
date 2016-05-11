<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="_view/mainstyle.css">
		<style type="text/css">
		table.account{
			position: fixed;
			top: 0;
			right: 0;
			border: 5px solid #ccc;
			background-color: white;
		}
		</style>
	</head>

	<body>
		<table>
			<tr>
				<td>
					<form action="${pageContext.servletContext.contextPath}/home" method="get">
						<input type="image" src="res/logo.gif.png" alt="YCP Logo" name="buttonPress"/>
					</form>
					
					<div>
						<table>
						<tr>
							<td>Pick</td>
							<td class="bookColHeading">Title</td>
							<td class="nameColHeading">Owner</td>
							<td class="isbnColHeading">Price</td>
			    		</tr>
			    		<form action="${pageContext.servletContext.contextPath}/buy-book" method="Post">
			    		<c:forEach items="${books}" var="bfs">
			        		<tr class="bookRow">
			        			<td><input type="checkbox" name="c${bfs.book.title}" value="${bfs.book.title}"></td>
			            		<td class="bookCol">${bfs.book.title}</td>
			            		<td class="nameCol">${bfs.owner.name}</td>
			            		<td class="isbnCol">${bfs.price}</td>			            
			        		</tr>
			    		</c:forEach> 
			    		<tr><td>
			    			<input type="submit" name="buttonPress" value="Buy">
			    		</td></tr>
			    		
			    		</form>
			    		</table>
					</div>
				</td>
				
				<td>
						<c:choose>
				<c:when test="${loggedin}">
					<div> 
						<table class="account">
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
							<td>
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
							</td>
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
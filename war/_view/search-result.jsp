<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Search</title>
		<link rel="stylesheet" type="text/css" href="_view/mainstyle.css">
	</head>

	<body>
		<table>
			<tr>
				<td>
					<div><img src="res/logo.gif.png" alt="YCP"></img></div>
					
					<form action="${pageContext.servletContext.contextPath}/search" method="post">
						<div>
							<input type="text" name="search" placeholder="Search by title, author, ISBN...">
						</div>
						
						<div>
						<table>
							<tr>
								<td>
  									<input name="bybutton" type="submit" value="Search by Title" />
								</td>
								<td>
  									<input name="bybutton" type="submit" value="Search by Author" />
								</td>
								<td>
									<input name="bybutton" type="submit" value="Search by ISBN" />
								</td>
							</tr>
						</table>
						</div>
						
					</form>
					
					<div>
					<table>
						<tr>
							<td class="bookColHeading">Title</td>
       						<td class="isbnColHeading">ISBN</td>
							<td class="nameColHeading">Last Name</td>
       						<td class="nameColHeading">First Name</td>
			    		</tr>
			    		<c:forEach items="${books}" var="book">
			        		<tr class="bookRow">
			            		<td class="bookCol">${book.title}</td>
			            		<td class="isbnCol">${book.isbn}</td>
			            		<td class="nameCol">${book.authorslastname}</td>
			            		<td class="nameCol">${book.authorsfirstname}</td>			            
			        		</tr>
			    		</c:forEach> 
			    		</table>
					</div>
				</td>
				
				<td>
					<form action="${pageContext.servletContext.contextPath}/login" method="GET">
  						<input name="buttonPress" type="submit" value="login" />
					</form>		
				</td>
			</tr>
		</table>
	</body>
</html>
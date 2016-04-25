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
				</td>
				
				<td>
				<c:choose>
				<c:when test="${loggedin}">
					<div> Account Info </div>
				</c:when>
				<c:otherwise>
					<form action="${pageContext.servletContext.contextPath}/login" method="post">
					
						<div>Username: <input name="Username" type="text" placeholder="Username"/> </div>
						<div>Password: <input name="Password" type="text" placeholder="Password"/> </div>
  						<div> 
  							<input name="buttonPress" type="submit" value="login" />
  							<input name="buttonPress" type="submit" value="create" />
  						</div>
					</form>
				</c:otherwise>
				</c:choose>	
				</td>
		</table>
		
		<div class="inlineImage">
			<img src="res/spartan.gif" alt="Smarty McSpartan"></img>
		</div>
		
		<form action= "${pageContext.servletContext.contextPath}/enterBook" method="get">
			<input type="submit" name="enter" value="Enter A Book"/>
		</form>
	</body>
</html>
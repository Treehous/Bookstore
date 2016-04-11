<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Search</title>
		<style type="text/css">
			.error {
				color: red;
				font-weight: bold;				
			}
			
			td.label {
				text-align: right;
			}
			
			td.book {
				text-align: center;
				color: blue;
				font-weight: bold;
			}
			
			td.bookColHeading {
				text-align: center;
				font-weight: bold;
				max-width: 400px;
			}
			
			td.isbnColHeading {
				text-align: center;
				font-weight: bold;
				max-width: 200px;
				padding-left: 20px;
			}
			
			td.nameColHeading {
				text-align: center;
				font-weight: bold;
				max-width: 300px;
				padding-left: 20px;
			}
			
			tr.bookRow {
				text-align: left;
				color: blue;
				font-weight: bold;
			}
			
			td.bookCol {
				text-align: left;
				color: blue;
				font-weight: bold;
				max-width: 400px;
				padding-left: 20px;				
			}
			
			td.isbnCol {
				text-align: left;
				color: blue;
				font-weight: bold;
				max-width: 200px;
				padding-left: 20px;
			}
			
			td.nameCol {
				text-align: left;
				color: blue;
				font-weight: bold;
				max-width: 400px;
				padding-left: 20px;				
			}						
		</style>
	</head>

	<body>
		<table>
			<tr>
				<td>
					<div><img src="res/logo.gif.png" alt="YCP"></img></div>
					
					<div>
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
					</div>
				</td>
				
				<td>
					<form action="${pageContext.servletContext.contextPath}/login" method="post">
  						<input name="buttonPress" type="submit" value="login" />
					</form>		
				</td>
		</table>
	</body>
</html>
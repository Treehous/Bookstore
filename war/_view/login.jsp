<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="mainstyle.css">
	</head>


	<body>
		<div><img src="res/logo.gif.png" alt="YCP"></img></div>
					
		<div>
			<form action="${pageContext.servletContext.contextPath}/login" method="post">
				<div> <input type="text" name="username" placeholder="Username"> </div>
				<div> <input type="text" name="pass1" placeholder="Password"></div>
				<div> <input type="text" name="pass2" placeholder="Re-enter Password"></div>
				<div> <input type="text" name="name" placeholder="Name"></div>
				<div> <input type="text" name="phone" placeholder="Phone Number"></div>
				<div> <input type="text" name="email" placeholder="Email Address"></div>
				<div> <input type="submit" name="submit" value="Create"> </div>
			</form>
		</div>

	</body>
</html>
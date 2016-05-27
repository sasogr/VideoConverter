<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="videoconverter.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>VideoConverter | Error</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/Site.css">
	<script type="text/javascript" src="js/jquery-2.2.3.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/angular.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
          <div class="container">
              <div class="navbar-header">
                  <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                      <span class="icon-bar"></span>
                      <span class="icon-bar"></span>
                      <span class="icon-bar"></span>
                  </button>

                  <a class="navbar-brand">VideoConverter</a>
              </div>
              <div style="height: 1px;" class="navbar-collapse navbar-responsive-collapse collapse">
	            <ul class="nav navbar-nav">
	                <li><a href="index.jsp">Home</a></li>
	                <li><a href="UploadServlet">Upload</a></li>
	                <li><a href="Convert">Convert</a></li>
	                <li><a href="DownloadServlet">Download</a></li>
	            </ul>
	
	            <ul class="nav navbar-nav navbar-right index-login-list">
	                <%
	                    HttpSession ses = request.getSession(true);
	                	SessionUser sessionUser = (SessionUser)ses.getAttribute("sessionUser");
	                    if(sessionUser != null){
	                        %>
	                        <li><a href="Logout">Hello <%=sessionUser.GetUsername()%>, logout</a></li>
	                    <%}
	                    else{
	                        %>
	                        <li><a href="LoginServlet">Login</a></li>
	                    <%}
	                %>                
	            </ul>
	        </div>
	    </div>
	</nav>
	
	<div class="container content-top-padding">
		<!-- HTML CODE HERE -->
		<%
			String errMessage = (String) request.getAttribute("errMessage");
		%>
		
		<h2>Error uploading a video: <%=errMessage%></h2>
		<br />
		<br />
		<a href="UploadServlet">Go back and upload new video!</a>
	</div>
	
	<div class="navbar navbar-fixed-bottom">
		<footer class="footer">
        	VideoConverter | &copy; 2016
        </footer>
	</div>
</body>
</html>
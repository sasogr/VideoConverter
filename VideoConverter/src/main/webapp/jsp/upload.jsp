<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="videoconverter.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>VideoConverter | Upload video</title>
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
	                <li class="active"><a href="UploadServlet">Upload</a></li>
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
	
	<div class="container content-top-padding content-upload">
		<!-- HTML CODE HERE -->
		<%
			String uploadedVideoName = (String) request.getAttribute("uploadedVideoName");
		%>
		
		<h2>Step 1. Video upload</h2>
		
		<%
			if(uploadedVideoName != null) {
				%>
				<p>
					Uploaded video: <%=uploadedVideoName%>
					
					<br />
					<a href="Convert">Convert the video</a>
				</p>
				<h4><b>OR</b></h4>
			<%}
		%>
		
		<form method="post" action="/VideoConverter/UploadServlet"
			enctype="multipart/form-data">
			Select file to upload: <input class="file-upload" type="file" name="file" size="60" /><br />
			<br /> <input class="btn btn-success" type="submit" value="Upload" />
		</form>
	</div>
	
	<div class="navbar navbar-fixed-bottom">
		<footer class="footer">
        	VideoConverter | &copy; 2016
        </footer>
	</div>
</body>
</html>
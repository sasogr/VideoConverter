<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload</title>
</head>
<body>
	<%
		String errMessage = (String) request.getAttribute("errMessage");
	%>
	
	<h2>Error uploading a video: <%=errMessage%></h2>
	<br />
	<br />
	<a href="UploadServlet">Go back and upload new video!</a>
</body>
</html>
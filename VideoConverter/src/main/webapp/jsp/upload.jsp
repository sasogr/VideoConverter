<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Video upload</title>
</head>
<body>
<center>
	<%
		String uploadedVideoName = (String) request.getAttribute("uploadedVideoName");
	%>
	
	<h1>Step 1. Video upload</h1>
	
	<%
		if(uploadedVideoName != null) {
			%>
			<p>
				Uploaded video: <%=uploadedVideoName%>
				
				<br />
				<a href="Convert">Convert the video</a>
				<br />
				<br />
				
				OR
			</p>
		<%}
	%>
	
	<form method="post" action="/VideoConverter/UploadServlet"
		enctype="multipart/form-data">
		Select file to upload: <input type="file" name="file" size="60" /><br />
		<br /> <input type="submit" value="Upload" />
	</form>
</center>
</body>
</html>
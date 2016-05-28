<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="videoconverter.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="videoConverterApp">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>VideoConverter | FFmpeg convert</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/Site.css">
	<script type="text/javascript" src="js/jquery-2.2.3.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/angular.min.js"></script>
	<script type="text/javascript" src="js/main.js"></script>
	<script type="text/javascript" src="js/ConvertController/convert.js"></script>
</head>
<body ng-controller="ConvertController">
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
	                <li class="active"><a href="Convert">Convert</a></li>
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
			String uploadedVideoName = (String) request.getAttribute("uploadedVideoName");
		%>
		<div id="container">
		    <div class="row">
		    	<div class="col-md-6">
		    		<h2>Step 2. Run commands</h2>
					<h3 class="text-primary">Uploaded video: <%=uploadedVideoName%></h3>
					<br />
					<div>
						<p>Choose a command:</p>
						<select name="commandSelect" id="commandSelect" ng-options="command as command.commandValue for command in commands" 
								ng-model="selectedCommand" ng-change="commandChange()">
							<option value="">-- please select --</option>
						</select>
					</div>
					<div>
						<p>Choose an option:</p>
						<select name="optionSelect" id="optionSelect" ng-options="option as option.commandValue for option in options" 
								ng-model="selectedOption"></select>
						
						<input type="text" name="optionValue" id="optionValue" ng-show="selectedOption.acceptInput == 1" ng-model="optionValue">
						
						<br />
						<br />
						<button class="btn btn-default" name="addOption" id="addOption" ng-click="addOption(selectedOption, optionValue)">Add option</button>
						<button class="btn btn-warning" name="clearOptions" id="clearOptions" ng-click="clearOptions()">Clear options</button>
						<button class="btn btn-success" name="executeCommand" id="executeCommand" ng-click="executeCommand('<%=sessionUser.GetUsername()%>')">Execute command</button>
					</div>
					<div>
						<h4>Preview of the command:</h4>
						{{commandToExecute}}{{optionsToExecute}}
					</div>
		    	</div>
		    	<div class="col-md-6">
		    		<div class="command-output-outer">
						<h4>Execution output:</h4>
						<div class="command-output-inner">
							{{terminalOutput}}
						</div>
						
						<button class="btn btn-warning" name="clearTerminal" id="clearTerminal" ng-click="clearTerminal()">Clear terminal</button>
					</div>
		    	</div>
		    </div>
	    </div>	
	</div>
	
	<div class="navbar navbar-fixed-bottom">
		<footer class="footer">
        	VideoConverter | &copy; 2016
        </footer>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="videoConverterApp">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>VideoConverter | FFmpeg</title>
	<link rel="stylesheet" type="text/css" href="css/Site.css">
	<script type="text/javascript" src="js/jquery-2.2.3.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/angular.min.js"></script>
	<script type="text/javascript" src="js/main.js"></script>
	<script type="text/javascript" src="js/ConvertController/convert.js"></script>
</head>
<body ng-controller="ConvertController">
	<%
		String uploadedVideoName = (String) request.getAttribute("uploadedVideoName");
	%>
	
	<h1>Step 2. Run commands</h1>
	
	<h2>Uploaded video: <%=uploadedVideoName%></h2>
	
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
		<button name="addOption" id="addOption" ng-click="addOption(selectedOption, optionValue)">Add option</button>
		<button name="clearOptions" id="clearOptions" ng-click="clearOptions()">Clear options</button>
		<button name="executeCommand" id="executeCommand" ng-click="executeCommand()">Execute command</button>
	</div>
	<div>
		<p>Preview of the command:</p>
		{{commandToExecute}}{{optionsToExecute}}
	</div>
	
	<div class="command-output-outer">
		<p>Execution output:</p>
		<div class="command-output-inner">
			{{terminalOutput.executionOutput}}
		</div>
		
		<button name="clearTerminal" id="clearTerminal" ng-click="clearTerminal()">Clear terminal</button>
	</div>
</body>
</html>
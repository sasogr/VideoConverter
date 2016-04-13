<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="videoConverterApp">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>VideoConverter | FFmpeg</title>
	<script type="text/javascript" src="js/jquery-2.2.3.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/angular.min.js"></script>
	<script type="text/javascript" src="js/main.js"></script>
	<script type="text/javascript" src="js/ConvertController/convert.js"></script>
</head>
<body ng-controller="ConvertController">
	<h1>Step 2. Run commands</h1>
	
	<p>Choose a command:</p>
	<select ng-change="changefunction(item)" ng-model="item">
		<option ng-repeat="item in commands" value="{{item.commandValue}}">{{item.name}}</option>
	</select>
</body>
</html>
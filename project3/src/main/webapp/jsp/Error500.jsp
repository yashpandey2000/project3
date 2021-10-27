<%@page import="in.co.rays.project3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page isErrorPage="true" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div align="center">
<form action="<%=ORSView.ERROR_CTL%>" method="get">
<h1 style="color: red">....oops!! No internet</h1>
<img src="img/image.gif" width="318" height="150" border="0">
<h2>Try:</h2>
<h3>
Checking the network cables, modem, and router<br>
  Running Windows Network Diagnostics<br>
  Reconnecting to Wi-Fi<br><br>
  <center><b >OR</b><br><br></center>
  Problem in Web-Application..!! Try after Some Time <br>
</h3>
<h2><a href="<%=ORSView.WELCOME_CTL%>">click here to go back</a></h2>
</form>
</div>
</body>
</html>
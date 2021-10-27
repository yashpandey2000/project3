<%@page import="in.co.rays.project3.controller.LoginCtl"%>
<%@page import="in.co.rays.project3.dto.RoleDTO"%>
<%@page import="in.co.rays.project3.controller.ORSView"%>
<%@page import="in.co.rays.project3.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<title>Insert title here</title>

<link rel="stylesheet" href="<%=ORSView.APP_CONTEXT%>/css/all.css" ></link>

<link rel="stylesheet"
href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
crossorigin="anonymous">

<link rel="stylesheet"
href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script
src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<style type="text/css">

.header {
   position: relative;
   width: 101%;
 height: 8%
}

</style>

</head>
<body>
<%
UserDTO userDto = (UserDTO) session.getAttribute("user");
boolean userLoggedIn = userDto != null;
String welcomeMsg = "Hi, ";
if (userLoggedIn) {
String role = (String) session.getAttribute("role");
welcomeMsg += userDto.getFirstname() + " (" + role + ")";
} else {
welcomeMsg += "Guest";
}
%>
<div class="header"  style="background-color:#000000;">

<nav class= "navbar navbar-expand-lg">
<a class="navbar-brand" href="<%=ORSView.WELCOME_CTL%>" >
<img src="<%=ORSView.APP_CONTEXT%>/img/JOBPORTAL-1583211843.png" width="135px" height="42px"></a>

<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aia-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
<span class="navbar-toggler-icon"></span>
</button>

<div class="collapse navbar-collapse" id="navbarNav">
<ul class="nav navbar-nav ml-auto">
<a class="nav-link" href="#"><span class="sr-only">(current)</span></a>

<%
if(userLoggedIn){
%>
<%
if(userDto.getRoleid()==RoleDTO.STUDENT){
%>

<div class="dropdown-menu" aria-labelledby="navbarDropdown">
<a class="dropdown-item" href="<%=ORSView.MARKSHEET_CTL%>"><i
class="far fa-file"></i>Add Marksheet</a> <a class="dropdown-item"
href="<%=ORSView.MARKSHEET_LIST_CTL%>"><i
class="fas fa-paste"></i>Marksheet List</a> <a class="dropdown-item"
href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><i
class=" far fa-file-alt"></i>Marksheet Merit List </a> <a
class="dropdown-item" href="<%=ORSView.GET_MARKSHEET_CTL%>"><i
class="far fa-copy"></i>Get Marksheet</a>
</div></li>

<%
}else if(userDto.getRoleid() == RoleDTO.ADMIN){
%>

<li class="nav-item dropdown" style="padding-left: 5xp;"><a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
role="button" data-toggle="dropdown" aria-haspopup="true"
aria-expanded="false"> <font style="color: white;">User</font></a>

<div class="dropdown-menu" aria-labelledby="navbarDropdown">
<a class="dropdown-item" href="<%=ORSView.USER_CTL%>"><i
class="fas fa-user-circle"></i>Add User</a> <a class="dropdown-item"
href="<%=ORSView.USER_LIST_CTL%>"><i
class="fas fa-user-friends"></i>User List</a>
</div></li>

<li class="nav-item dropdown" style="padding-left: 5px"><a
class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
role="button" data-toggle="dropdown" aria-haspopup="true"
aria-expanded="false"> <font style="color: white;">Marksheet
</font></a>

<div class="dropdown-menu" aria-labelledby="navbarDropdown">
<a class="dropdown-item" href="<%=ORSView.MARKSHEET_CTL%>"><i
class="far fa-file"></i>Add Marksheet</a> <a class="dropdown-item"
href="<%=ORSView.MARKSHEET_LIST_CTL%>"><i
class="fas fa-paste"></i>Marksheet List</a> <a class="dropdown-item"
href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><i
class=" far fa-file-alt"></i>Marksheet Merit List </a> <a
class="dropdown-item" href="<%=ORSView.GET_MARKSHEET_CTL%>"><i
class="far fa-copy"></i>Get Marksheet</a>
</div></li>

<li class="nav-item dropdown" style="padding-left: 5px"><a
class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
role="button" data-toggle="dropdown" aria-haspopup="true"
aria-expanded="false"> <font style="color: white;">Role</font>
</a>

<div class="dropdown-menu" aria-labelledby="navbarDropdown">
<a class="dropdown-item" href="<%=ORSView.ROLE_CTL%>"><i
class="fas fa-user-tie"></i>Add Role</a> <a class="dropdown-item"
href="<%=ORSView.ROLE_LIST_CTL%>"><i
class="fas fa-user-friends"></i>Role List</a>
</div></li>

<li class="nav-item dropdown" style="padding-left: 5px"><a
class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
role="button" data-toggle="dropdown" aria-haspopup="true"
aria-expanded="false"> <font style="color: white;">College</font>
</a>

<div class="dropdown-menu" aria-labelledby="navbarDropdown">
<a class="dropdown-item" href="<%=ORSView.COLLEGE_CTL%>"><i
class="fas fa-university"></i>Add College</a> <a
class="dropdown-item" href="<%=ORSView.COLLEGE_LIST_CTL%>"><i
class="fas fa-building"></i>College List </a>
</div></li>

<li class="nav-item dropdown" style="padding-left: 5px"><a
class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
role="button" data-toggle="dropdown" aria-haspopup="true"
aria-expanded="false"> <font style="color: white;">Course
</font></a>

<div class="dropdown-menu" aria-labelledby="navbarDropdown">
<a class="dropdown-item" href="<%=ORSView.COURSE_CTL%>"><i
class="fas fa-book-open"></i>Add Course</a> <a class="dropdown-item"
href="<%=ORSView.COURSE_LIST_CTL%>"><i
class="fas fa-sort-amount-down"></i>Course List </a>
</div></li>


<li class="nav-item dropdown" style="padding-left: 5px"><a
class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
role="button" data-toggle="dropdown" aria-haspopup="true"
aria-expanded="false"> <font style="color: white;">Student</font>
</a>

<div class="dropdown-menu" aria-labelledby="navbarDropdown">
<a class="dropdown-item" href="<%=ORSView.STUDENT_CTL%>"><i
class="far fa-user-circle"></i>Add Student</a> <a
class="dropdown-item" href="<%=ORSView.STUDENT_LIST_CTL%>"><i
class="fas fa-users"></i>Student List</a>
</div></li>

<li class="nav-item dropdown" style="padding-left: 5px"><a
class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
role="button" data-toggle="dropdown" aria-haspopup="true"
aria-expanded="false"> <font style="color: white;">Faculty
</font></a>

<div class="dropdown-menu" aria-labelledby="navbarDropdown">
<a class="dropdown-item" href="<%=ORSView.FACULTY_CTL%>"><i
class="fas fa-user-tie"></i>Add Faculty</a> <a class="dropdown-item"
href="<%=ORSView.FACULTY_LIST_CTL%>"><i class=" fas fa-users"></i>Faculty
List</a>
</div></li>

<li class="nav-item dropdown" style="padding-left: 5px"><a
class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
role="button" data-toggle="dropdown" aria-haspopup="true"
aria-expanded="false"> <font style="color: white;">Time
Table</font>
</a>

<div class="dropdown-menu" aria-labelledby="navbarDropdown">
<a class="dropdown-item" href="<%=ORSView.TIMETABLE_CTL%>"><i
class="fas fa-clock"></i>Add TimeTable</a> <a class="dropdown-item"
href="<%=ORSView.TIMETABLE_LIST_CTL%>"><i
class="far fa-clock"></i>TimeTable List</a>
</div></li>


<li class="nav-item dropdown" style="padding-left: 5px"><a
class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
role="button" data-toggle="dropdown" aria-haspopup="true"
aria-expanded="false"> <font style="color: white;">Subject</font>
</a>

<div class="dropdown-menu" aria-labelledby="navbarDropdown">
<a class="dropdown-item" href="<%=ORSView.SUBJECT_CTL%>"><i
class="fas fa-calculator"></i>Add Subject</a> <a
class="dropdown-item" href="<%=ORSView.SUBJECT_LIST_CTL%>"> <i
class="fas fa-sort-amount-down"></i>Subject List
</a>
</div></li>

<%
}
}
%>


<li class="nav-item dropdown"
style="padding-left: 5px; padding-right: 67px"><a
class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
role="button" data-toggle="dropdown" aria-haspopup="true"
aria-expanded="false"><font style="color: white;"><%=welcomeMsg%>
</font></a>
<div class="dropdown-menu" aria-labelledby="navbarDropdown">

<%
if (userLoggedIn) {
%>

<a class="dropdown-item"
href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>"><i
class="fas fa-sign-out-alt"></i>Logout </a> <a class="dropdown-item"
href="<%=ORSView.MY_PROFILE_CTL%>"><i class="fas fa-user-tie"></i>My
Profile</a> <a class="dropdown-item"
href="<%=ORSView.CHANGE_PASSWORD_CTL%>"> <i
class="fas fa-edit"></i>Change Password
</a> <a class="dropdown-item" href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank"><i
class="fas fa-clone"></i>Java Doc </a>

<%
} else {
%>

<a class="dropdown-item" href="<%=ORSView.LOGIN_CTL%>"><i
class="fas fa-sign-in-alt"><b>Login</b></i> <a class="dropdown-item"
href="<%=ORSView.USER_REGISTRATION_CTL%>"><i
class="fas fa-registered"><b>User Registration</b></i></a>
</div></li>

<%
}
%>

</ul>
</div>
</nav>
</div>
</body>
</html>
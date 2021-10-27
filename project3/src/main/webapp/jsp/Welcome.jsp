<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<style type="text/css">

  #magic{
    background: rgba(0, 0, 0, 0.500);;
  
} 

video-wrap {
  clip: rect(0, auto, auto, 0);
  position: absolute;
  top: 0;
  left: 0;
  width: 30%;
  height: 50%;
}

#video {
  position: fixed;
  display: block;
  top: 0;
  left: 0;
  width: 80%;
  height: 10%;
  background-size: cover;
  background-position: center;
  -webkit-transform: translateZ(0);
          transform: translateZ(0);
  will-change: transform;
  z-index: -100;
}
video {
  position: fixed;
  top: 30%;
  left: 30%;
  min-width: 10%;
  min-height: 10%;
  width: auto;
  height: auto;
  z-index: -10;
  transform: translateX(-50%) translateY(-50%);
  background: url('') no-repeat;
  background-size: cover;
  transition: 1s opacity;
}

.row, .container-fluid {
margin-left: 0px!important;
margin-right: 0px!important;
}

</style>


</head>
<body >

<div id="magic">
  <div class="video-wrap">
<div id="video">
 <video id="bgvid" autoplay loop muted>
<source src="/project3/Sky.mp4"" type="video/mp4">  
</video></div> 


<div class="header" >
<%@include file="Header.jsp" %>

 <form action="<%=ORSView.WELCOME_CTL%>" >
            
            
            <br><br><br><br><br><br><br><br><br><br><br>
            
            
            <h1 align="center">
            <font size="10px" color="white" style="font-size: 70px">Welcome To ORS</font>
            </h1>

<div class="footer">
<%@include file="Footer.jsp" %>
</div>

<br><br><br><br><br><br><br><br><br><br><br><br>

</body>
</html>
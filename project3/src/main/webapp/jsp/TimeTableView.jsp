<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project3.util.HTMLUtility"%>
<%@page import="in.co.rays.project3.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project3.util.ServletUtility"%>
<%@page import="in.co.rays.project3.controller.TimeTableCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<style type="text/css">

#magic{
    background: rgba(0, 0, 0, 0.300);
    
    
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
  width: 100%;
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
<body>


<div id="magic">
  <div class="video-wrap">
<div id="video">
 <video id="bgvid" autoplay loop muted>
<source src="/project3/Sky.mp4"" type="video/mp4">  
</video></div>


<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="Calendar.jsp" %>
	</div>
	<div>
		<main>
		<form action="<%=ORSView.TIMETABLE_CTL%>" method="post">
			<div class="row pt-3 pb-3">
				<jsp:useBean id="dto" class="in.co.rays.project3.dto.TimeTableDTO"
					scope="request"></jsp:useBean>
				<!-- Grid column -->
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card">
						<div class="card-body">
							<%
								long id = DataUtility.getLong(request.getParameter("id"));
								if (id > 0) {
							%>
							<h3 class="text-center text-primary">Update Time Table</h3>
							<%
								} else {
							%>
							<h3 class="text-center text-primary">Add Time Table</h3>
							<%
								}
							%>
							<!--Body-->
							<div>
								<H6 align="center">
									<%
										if (!ServletUtility.getSuccessMessage(request).equals("")) {
									%>
									<div class="alert alert-success alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getSuccessMessage(request)%>
									</div>
									<%
										}
									%>
								</H6>
								<H6 align="center">
									<%
										if (!ServletUtility.getErrorMessage(request).equals("")) {
									%>
									<div class="alert alert-danger alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
											<%=ServletUtility.getErrorMessage(request)%>
									</div>
									<%
										}
									%>
								</H6>
								<%
									List l = (List) request.getAttribute("courseList");
									List li = (List) request.getAttribute("subjectList");
								%>
								<input type="hidden" name="id" value="<%=dto.getId()%>">
								<input type="hidden" name="createdBy"
									value="<%=dto.getCreatedby()%>"> <input type="hidden"
									name="modifiedBy" value="<%=dto.getModifiedby()%>"> <input
									type="hidden" name="createdDatetime"
									value="<%=DataUtility.getTimestamp(dto.getCreateddatetime())%>">
								<input type="hidden" name="modifiedDatetime"
									value="<%=DataUtility.getTimestamp(dto.getModifieddatetime())%>">
							</div>
	
	<div class="md-form">
	<div class="col-sm-12">
      <div class="input-group">
      <label><b>Course</b></label><span style="color: red;">*</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-envelope grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <%=HTMLUtility.getList("courseid", String.valueOf(dto.getCourseid()), l)%>
      </div></div>
	&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("coursename", request)%></font></br>
	

<div class="col-sm-12">
      <div class="input-group">
      <label><b>Subject</b></label><span style="color: red;">*</span> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-book grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <%=HTMLUtility.getList("subjectid", String.valueOf(dto.getSubjectid()), li)%>
      </div></div>
		&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("subjectname", request)%></font></br>
			
	
	<div class="col-sm-12">
      <div class="input-group">
      <label><b>Semester</b></label><span style="color: red;">*</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-sort grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <%
        LinkedHashMap map = new LinkedHashMap();
        map.put("1st", "1st");
        map.put("2nd", "2nd");
        map.put("3rd", "3rd");
        map.put("4th", "4th");
        map.put("5th", "5th");
        map.put("6th", "6th");
        map.put("7th", "7th");
        map.put("8th", "8th");
        
										
										String htmlList = HTMLUtility.getList("sem", dto.getSemester(), map);
									%>
									<%=htmlList%>
      </div></div>
	&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("sem", request)%></font></br>
	
	
	<div class="col-sm-12">
      <div class="input-group">
      <label><b>Exam Date</b></label><span style="color: red;">*</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <input type="text" class="form-control"  name="examdate" placeholder="Enter Exam Date" id="datepicker" readonly="readonly"
									value="<%=DataUtility.getDateString(dto.getExamdate())%>">
      </div>
    </div>	
	&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("examdate", request)%></font></br>
	

	<div class="col-sm-12">
      <div class="input-group">
      <label><b>Exam Time</b></label><span style="color: red;">*</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-clock grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <%
										HashMap map1 = new HashMap();
										map1.put("08:00 AM to 11:00 AM", "08:00 AM to 11:00 AM");
										map1.put("12:00PM to 3:00PM", "12:00PM to 3:00PM");
										map1.put("3:00PM to 6:00PM", "3:00PM to 6:00PM");
										String htmlList1 = HTMLUtility.getList("examtime", dto.getExamtime(), map1);
									%>
									<%=htmlList1%>
      </div></div>
	&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("examtime", request)%></font></br>
	
							
							</br>
							
							<%
								if (id>0) {
							%>
							<div class="text-center">
								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=TimeTableCtl.OP_UPDATE%>"> <input
									type="submit" name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=TimeTableCtl.OP_CANCEL%>">
							</div>
							<%
								} else {
							%>
							<div class="text-center">
								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=TimeTableCtl.OP_SAVE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=TimeTableCtl.OP_RESET%>">
							</div>
							<%
								}
							%>
						</div>
					</div>
				</div>
				<div class="col-md-4 mb-4"></div>
				</div>
				
				<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
				<br><br><br><br><br><br><br>
				
		</form>
		</main>
	</div>
</body>
<%@include file="Footer.jsp"%>

</body>
</html>
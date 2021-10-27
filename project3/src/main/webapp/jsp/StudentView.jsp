<%@page import="java.util.List"%>
<%@page import="in.co.rays.project3.util.HTMLUtility"%>
<%@page import="in.co.rays.project3.util.ServletUtility"%>
<%@page import="in.co.rays.project3.util.DataUtility"%>
<%@page import="in.co.rays.project3.controller.StudentCtl"%>
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
		<jsp:useBean id="dto" class="in.co.rays.project3.dto.StudentDTO"
			scope="request"></jsp:useBean>
		<main>
		<form action="<%=ORSView.STUDENT_CTL%>" method="post">

			<div class="row pt-3 pb-3">
				<!-- Grid column -->
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card">
						<div class="card-body">
							<%
								long id = DataUtility.getLong(request.getParameter("id"));
								if (id > 0) {
							%>
							<h3 class="text-center default-text text-primary">Update Student</h3>
							<%
								} else {
							%>
							<h3 class="text-center default-text text-primary">Add Student</h3>
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

								<input type="hidden" name="id" value="<%=dto.getId()%>">
								<input type="hidden" name="createdBy"
									value="<%=dto.getCreatedby()%>"> <input type="hidden"
									name="modifiedBy" value="<%=dto.getModifiedby()%>"> <input
									type="hidden" name="createdDatetime"
									value="<%=DataUtility.getTimestamp(dto.getCreateddatetime())%>">
								<input type="hidden" name="modifiedDatetime"
									value="<%=DataUtility.getTimestamp(dto.getModifieddatetime())%>">
							</div>
							<%
								List li = (List) request.getAttribute("collegeList");
							%>

	
	<div class="col-sm-12">
      <div class="input-group">
      <label><b>College</b></label><span style="color: red;">*</span> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-building grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <%=HTMLUtility.getList("college", String.valueOf(dto.getCollegeid()), li)%>
      </div></div>	
	&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  <font color="red" class="pl-sm-5">  <%=ServletUtility.getErrorMessage("college", request)%></font></br>								
									
	
		<div class="col-sm-12">
      <div class="input-group">
      <label><b>First Name</b></label><span style="color: red;">*</span> &nbsp; &nbsp; &nbsp; &nbsp;
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-user-alt grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <input type="text" class="form-control" name="firstname" placeholder="First Name" value="<%=DataUtility.getStringData(dto.getFirstname())%>">
      </div>
    </div>
	&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("firstname", request)%></font></br>			
	
	
    <div class="col-sm-12">
      <div class="input-group">
      <label><b>Last Name</b></label><span style="color: red;">*</span>  &nbsp; &nbsp; &nbsp; &nbsp;
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-user-circle grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <input type="text" class="form-control" name="lastname" placeholder="Last Name" value="<%=DataUtility.getStringData(dto.getLastname())%>">
      </div>
    </div>
	&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("lastname", request)%></font></br>		
	
	
	<div class="col-sm-12">
      <div class="input-group">
      <label><b>Email Id</b></label><span style="color: red;">*</span>  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-envelope grey-text" style="font-size: 1rem;"></i> </div>
        </div>
       <input type="text" name="email" class="form-control" placeholder="email Id"
									value="<%=DataUtility.getStringData(dto.getEmailid())%>">
      </div>
    </div>
	&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("email", request)%></font></br>
	
	
	<div class="col-sm-12">
      <div class="input-group">
      <label><b>Mobile No</b></label><span style="color: red;">*</span> &nbsp; &nbsp; &nbsp;  &nbsp;
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-phone-square grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <input type="text" class="form-control" id="defaultForm-email" maxlength="10" name="mobile" placeholder="mobile No" value="<%=DataUtility.getStringData(dto.getMobileno())%>">
      </div>
    </div>							
	&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("mobile", request)%></font></br>
	
	
	<div class="col-sm-12">
      <div class="input-group">
      <label><b>DOB</b></label><span style="color: red;">*</span> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <input type="text" id="datepicker" name="dob" class="form-control" placeholder="Date Of Birth" readonly="readonly" value="<%=DataUtility.getDateString(dto.getDob()) %>">
      </div>
    </div>	
	 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("dob", request)%></font></br>
							
							<br>
							
							<%
									if(id>0) {
								%>
								<div class="text-center">

									<input type="submit" class="btn btn-success" name="operation" 
										value="<%=StudentCtl.OP_UPDATE%>"> 
										<input
										type="submit" class="btn btn-warning" name="operation"
										value="<%=StudentCtl.OP_CANCEL%>">

								</div>
								<%
									} else {
								%>
								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=StudentCtl.OP_SAVE%>"> &nbsp; <input type="submit"
										name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=StudentCtl.OP_RESET%>">

								</div>
								<%
									}
								%>
							</div>
						</div>
					</div>
					
					</div>
					<div class="col-md-4 mb-4"></div>
					</div>
					
					<br><br><br>
					
		</form>
		</main>


	</div>

</body>
<%@include file="Footer.jsp"%>


</body>
</html>
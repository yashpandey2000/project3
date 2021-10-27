<%@page import="in.co.rays.project3.util.DataUtility"%>
<%@page import="in.co.rays.project3.util.ServletUtility"%>
<%@page import="in.co.rays.project3.controller.CollegeCtl"%>
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
	</div>
	<div>
	
	<br>
	

		<main>
		<form action="<%=ORSView.COLLEGE_CTL%>" method="post">

			<div class="row pt-3 pb-4">
				<!-- Grid column -->
				<jsp:useBean id="dto" class="in.co.rays.project3.dto.CollegeDTO"
					scope="request"></jsp:useBean>
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card">
						<div class="card-body">
							<%
								long id = DataUtility.getLong(request.getParameter("id"));
								if (id > 0) {
							%>
							<h3 class="text-center text-primary">Update College</h3>
							<%
								} else {
							%>
							<h3 class="text-center text-primary">Add College</h3>
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
							<div class="md-form">
								
		<div class="col-sm-12">
      <div class="input-group">
      <label><b>Name</b></label> 
	  <span style="color: red;">*</span> &nbsp;&nbsp; &nbsp;&nbsp; 
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-university grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <input type="text" name="cname"  class="form-control"
									id="defaultForm-email" 
									placeholder="Enter Name"
									value="<%=DataUtility.getStringData(dto.getName())%>">
      </div>
    </div>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("cname", request)%></font></br>			
		
								

								
	<div class="col-sm-12">
      <div class="input-group">
      <label></label><b>Address</b>
	  <span style="color: red;">*</span>  &nbsp;&nbsp;
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-address-book grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <input type="text" name="caddress" class="form-control"
									placeholder="Enter Address"
									value="<%=DataUtility.getStringData(dto.getAddress())%>">
      </div>
    </div>	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("caddress", request)%></font></br>
									
									
								
	<div class="col-sm-12">
      <div class="input-group">
      <label><b>State</b></label>
	  <span style="color: red;">*</span> &nbsp; &nbsp; &nbsp; &nbsp;
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-address-card grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <input type="text" name="cstate" class="form-control" placeholder="Enter State" value="<%=DataUtility.getStringData(dto.getState())%>">
      </div>
    </div>	
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("cstate", request)%></font></br>
									

								
		<div class="col-sm-12">
      <div class="input-group">
      <label ><b>City</b></label>
	<span style="color: red;">*</span> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-address-card grey-text" style="font-size: 1rem;"></i> </div>
        </div>
       <input type="text" name="ccity" class="form-control" placeholder="Enter City" value="<%=DataUtility.getStringData(dto.getCity())%>">
      </div>
    </div>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("ccity", request)%></font><br>
									

								
	<div class="col-sm-12">
      <div class="input-group">
        <div class="input-group-prepend">
        <label><b>Mobile</b></label>
		<span style="color: red;">*</span>  &nbsp; &nbsp;&nbsp; 
          <div class="input-group-text"><i class="fa fa-phone grey-text" style="font-size: 1rem;"></i> </div>
        </div>
       <input type="text"  class="form-control"
									name="cphone" placeholder="Enter MobileNo" maxlength="10"
									value="<%=DataUtility.getStringData(dto.getPhoneno())%>">
      </div>
    </div>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("cphone", request)%></font></br>
    
								
							</div>
							</br>
							<%
								if (id > 0) {
							%>
							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=CollegeCtl.OP_UPDATE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=CollegeCtl.OP_CANCEL%>">
							</div>
							<%
								} else {
							%>
							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=CollegeCtl.OP_SAVE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=CollegeCtl.OP_RESET%>">
							</div>
							<%
								}
							%>
						</div>
					</div>
				</div>
				<div class="col-md-4 mb-4"></div>
			</div>
			
			
			<br><br><br><br>

		</form>
		</main>


	</div>

</body>
<%@include file="Footer.jsp"%>

</body>
</html>
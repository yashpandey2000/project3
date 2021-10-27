<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project3.util.HTMLUtility"%>
<%@page import="in.co.rays.project3.util.ServletUtility"%>
<%@page import="in.co.rays.project3.util.DataUtility"%>
<%@page import="in.co.rays.project3.controller.CourseCtl"%>
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
		<jsp:useBean id="dto" class="in.co.rays.project3.dto.CourseDTO"
			scope="request"></jsp:useBean>
			
			<br>

		<main>
		<form action="<%=ORSView.COURSE_CTL%>" method="post">

			<div class="row pt-3 pb-3">
				<!-- Grid column -->
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card">
						<div class="card-body">
							<%
							  long id=DataUtility.getLong(request.getParameter("id"));
							
							
							if (id >0)  {
							%>
							<h3 class="text-center default-text text-primary">Update Course</h3>
							<%
								} else {
							%>
							<h3 class="text-center default-text text-primary">Add Course</h3>
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
       <label><b>Course Name</b></label>
	    <span style="color:red;">*</span> &nbsp;&nbsp;&nbsp;
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-book grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <input type="text" class="form-control" name="cname" placeholder="Enter Course Name" value="<%=DataUtility.getStringData(dto.getCoursename())%>">
      </div>
    </div>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("cname", request)%></font></br>							
								
	
	<div class="col-sm-12">
      <div class="input-group">
      <label><b>Duration</b></label>
	<span style="color:red;">*</span> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-clock grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        
									<%
										HashMap map = new HashMap();
										map.put("1Year", "1Year");
										map.put("2Year", "2Year");
										map.put("3Year", "3Year");
										map.put("4Year", "4Year");
										map.put("5Year", "5Year");
										String HtmlList = HTMLUtility.getList("duration", dto.getDuration(), map);
									%>
									<%=HtmlList%></div>
      
    </div>		
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("duration", request)%></font></br>
								
							
								
	  <div class="col-sm-12">
      <div class="input-group">
      <label><b>Description</b></label>
	   <span style="color:red;">*</span> &nbsp; &nbsp; &nbsp; &nbsp;
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-list grey-text" style="font-size: 1rem;"></i> </div>
        </div>
       <textarea name="desc" placeholder="Enter description" class="form-control"
		 rows="5" cols="5"><%=DataUtility.getStringData(dto.getDescription())%></textarea>

      </div>
    </div>
							</br>
							<%
								if(id>0) {
							%>
							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=CourseCtl.OP_UPDATE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=CourseCtl.OP_CANCEL%>">
							</div>
							<%
								} else {
							%>
							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=CourseCtl.OP_SAVE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=CourseCtl.OP_RESET%>">
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
				<br><br><br><br><br><br>
		</form>
		</main>


	</div>

</body>
<%@include file="Footer.jsp"%>


</body>
</html>
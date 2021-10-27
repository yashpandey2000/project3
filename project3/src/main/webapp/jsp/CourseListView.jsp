<%@page import="java.util.List"%>
<%@page import="in.co.rays.project3.dto.CourseDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project3.util.DataUtility"%>
<%@page import="in.co.rays.project3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project3.util.ServletUtility"%>
<%@page import="in.co.rays.project3.controller.CourseListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
	
	
	<style type="text/css">
	
	body{
    background-image:  url('../img/photo.jpeg');
    background-position: center;
   background-size: cover;
   
}
	
	
	.text{
	
	text-align: center;
	
	}
	</style>


</head>
<body>


<div>
		<%@include file="Header.jsp"%>
	</div>
	<div>
		<form action="<%=ORSView.COURSE_LIST_CTL%>" method="post">




			<jsp:useBean id="dto" class="in.co.rays.project3.dto.CourseDTO"
				scope="request"></jsp:useBean>
			<%
				List list1 = (List) request.getAttribute("courseList");
			%>
			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				List list = ServletUtility.getList(request);
				Iterator<CourseDTO> it = list.iterator();
				if (list.size() != 0) {
			%>
			<center>
				<h1 class=" font-weight-bold pt-3"><u>Course List</u></h1>
				<center>
				
				<br>
				<br>

					<div class="row">
						<div class="col-md-4"></div>

						<%
							if (!ServletUtility.getSuccessMessage(request).equals("")) {
						%>

						<div class="col-md-4 alert alert-success alert-dismissible"
							style="background-color: #80ff80" align="center">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<h4>
								<font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font>
							</h4>
						</div>
						<%
							}
						%>

						<div class="col-md-4"></div>
					</div>
					<div class="row">
						<div class="col-md-4"></div>

						<%
							if (!ServletUtility.getErrorMessage(request).equals("")) {
						%>
						<div class=" col-md-4 alert alert-danger alert-dismissible" align="center">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<h4>
								<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
							</h4>
						</div>
						<%
							}
						%>
						<div class="col-md-4"></div>
					</div>
					<div class="row">

						<div class="col-sm-3"></div>
						<div class="col-sm-2">
							<%=HTMLUtility.getList("course", String.valueOf(dto.getId()), list1)%>
						</div>
						<div class="col-sm-2">
							<%
								HashMap map = new HashMap();
									map.put("1y", "1year");
									map.put("2y", "2year");
									map.put("3y", "3year");
									map.put("4y", "4year");
									map.put("5y", "5year");
									String HtmlList = HTMLUtility.getList("duration", dto.getDuration(), map);
							%><%=HtmlList%>
						</div>

						<div class="col-sm-2">
							<input type="submit" class="btn btn-primary btn-md"
								style="font-size: 17px" name="operation"
								value="<%=CourseListCtl.OP_SEARCH%>">&emsp; <input
								type="submit" class="btn btn-dark btn-md"
								style="font-size: 17px" name="operation"
								value="<%=CourseListCtl.OP_RESET%>">
						</div>

						<div class="col-sm-3"></div>
					</div>







					</br>
					<div style="margin-bottom: 20px;" class="table-responsive">
						<table class="table table-striped table-hover table-bordered">
							<thead>
								<tr style="background-color: #8C8C8C;">

									<th width="10%"><input type="checkbox" id="select_all"
										name="Select" class="text"> Select All</th>
									<th class="text">S.NO</th>
									<th class="text">Course Name</th>
									<th class="text">Duration</th>
									<th class="text">Description</th>
									<th class="text">Edit</th>
								</tr>
							</thead>
							<%
								while (it.hasNext()) {
										dto = it.next();
							%>

							<tbody>
								<tr style="font-weight: bold;">
									<td align="left"><input type="checkbox" class="checkbox"
										name="ids" value="<%=dto.getId()%>"></td>
									<td align="center"><%=index++%></td>
									<td align="center"><%=dto.getCoursename()%></td>
									<td align="center"><%=dto.getDuration()%></td>
									<td align="center"><%=dto.getDescription()%></td>
								<div class="input-group">	<td align="center"><a href="CourseCtl?id=<%=dto.getId()%>"><i class="fas fa-edit" grey-text" style="font-size: 1rem;">Edit</i></a></td></div>
								</tr>
							</tbody>
							<%
								}
							%>
						</table>


					</div>
					<table width="100%">
						<tr>
							<td><input type="submit" name="operation"
								class="btn btn-secondary btn-md" style="font-size: 17px"
								value="<%=CourseListCtl.OP_PREVIOUS%>"
								<%=pageNo > 1 ? "" : "disabled"%>></td>
							<td><input type="submit" name="operation"
								class="btn btn-warning btn-md" style="font-size: 17px"
								value="<%=CourseListCtl.OP_NEW%>"></td>
							<td><input type="submit" name="operation"
								class="btn btn-danger btn-md" style="font-size: 17px"
								value="<%=CourseListCtl.OP_DELETE%>"></td>

							<td align="right"><input type="submit" name="operation"
								class="btn btn-secondary btn-md" style="font-size: 17px"
								style="padding: 5px;" value="<%=CourseListCtl.OP_NEXT%>"
								<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
						</tr>
						<tr></tr>
					</table>
					</br>
					<%
						}
						if (list.size() == 0) {
							System.out.println("user list view list.size==0");
					%>
					<center>
						<h1 class=" font-weight-bold pt-3"><U>Course List</U></h1>
					</center>

					</br>
					<div class="row">
						<div class="col-md-4"></div>

						<%
							if (!ServletUtility.getErrorMessage(request).equals("")) {
						%>
						<div class=" col-md-4 alert alert-danger alert-dismissible" align="center">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<h4>
								<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
							</h4>
						</div>
						<%
							}
						%>
						<div class="col-md-4"></div>
					</div>
					 </br>
					<div style="padding-left: 48%;">
						<input type="submit" name="operation"
							class="btn btn-primary btn-md" style="font-size: 17px"
							value="<%=CourseListCtl.OP_BACK%>">
					</div>
					<%
						}
					%>
					<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
						type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>

	</div>

</body>
<%@include file="Footer.jsp"%>

</body>
</html>
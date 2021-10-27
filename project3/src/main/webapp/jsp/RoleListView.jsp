<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project3.util.HTMLUtility"%>
<%@page import="in.co.rays.project3.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project3.controller.RoleListCtl"%>
<%@page import="in.co.rays.project3.util.ServletUtility"%>
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
		<form action="<%=ORSView.ROLE_LIST_CTL%>" method="post">





			<jsp:useBean id="dto" class="in.co.rays.project3.dto.RoleDTO"
				scope="request"></jsp:useBean>
			<%
				List list1 = (List) request.getAttribute("roleList");
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				List list = ServletUtility.getList(request);
				Iterator<RoleDTO> it = list.iterator();
				if (list.size() != 0) {
			%>

			<center>
				<h1 class=" font-weight-bold pt-3"><u>Role
					List</u></h1>
			</center>
			</br>

			<div class="row">
				<div class="col-md-4"></div>
				
					<%
						if (!ServletUtility.getSuccessMessage(request).equals("")) {
					%>

					<div class="col-md-4 alert alert-success alert-dismissible" align="center"  style="background-color: #80ff80">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<h4><font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font></h4>
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
					<h4><font color="red">	<%=ServletUtility.getErrorMessage(request)%></font></h4>
					</div>
					<%
						}
					%>
					<div class="col-md-4"></div>
			</div>
			<div class="row">
				<div class="col-sm-4"></div>

				<div class="col-sm-3"><%=HTMLUtility.getList("name", String.valueOf(dto.getId()), list1)%></div>
				<div class="col-sm-2">
					<input type="submit" class="btn btn-primary btn-md"
						style="font-size: 17px" name="operation"
						value="<%=RoleListCtl.OP_SEARCH%>">&emsp;
						
					<input type="submit" class="btn btn-dark btn-md"
						style="font-size: 17px" name="operation"
						value="<%=RoleListCtl.OP_RESET%>">
				</div>
				
				<div class="col-sm-2"></div>
			</div>
			

			</br>
			<div style="margin-bottom: 20px;" class="table-responsive">
				<table class="table table-striped table-hover table-bordered">
					<thead>
						<tr style="background-color: #8C8C8C;">

							<th width="10%"><input type="checkbox" id="select_all"
								name="Select" class="text"> Select All</th>
							<th class="text">S.NO</th>
							<th class="text">Name</th>
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
								name="ids" value="<%=dto.getId()%>"
								<%if (dto.getId() == RoleDTO.ADMIN) {%> <%="disabled"%> <%}%>></td>
							<td align="center"><%=index++%></td>
							<td align="center"><%=dto.getName()%></td>
							<td align="center"><%=dto.getDescription()%></td>
							<div class="input-group">
							
							<td align = "center">
<%if(dto.getId()!=1){ %>

   <a href="RoleCtl?id=<%=dto.getId()%>"><i class="fas fa-edit" grey-text" style="font-size: 1rem;">Edit</a></i>

</td>
<%}%>
							
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
							value="<%=RoleListCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>></td>
						<td><input type="submit" name="operation"
							class="btn btn-warning btn-md" style="font-size: 17px"
							value="<%=RoleListCtl.OP_NEW%>"></td>
						<td><input type="submit" name="operation"
							class="btn btn-danger btn-md" style="font-size: 17px"
							value="<%=RoleListCtl.OP_DELETE%>"></td>

						<td align="right"><input type="submit" name="operation"
							class="btn btn-secondary btn-md" style="font-size: 17px"
							style="padding: 5px;" value="<%=RoleListCtl.OP_NEXT%>"
							<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
					</tr>
					<tr></tr>
				</table>
		
			<%
				}
				if (list.size() == 0) {
					System.out.println("user list view list.size==0");
			%>
			<center>
				<h1 style="font-size: 40px; padding-top: 24px; color: #162390;"><u>Role
					List</u></h1>
			</center>
			</br>
			<div class="row">
				<div class="col-md-4"></div>
				
					<%
						if (!ServletUtility.getErrorMessage(request).equals("")) {
					%>
					<div class=" col-md-4 alert alert-danger alert-dismissible" align="center">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4><font color="red">	<%=ServletUtility.getErrorMessage(request)%></font></h4>
					</div>
					<%
						}
					%>
					<div class="col-md-4"></div>
			</div>
			</br>
			<div style="padding-left: 48%;">
				<input type="submit" name="operation" class="btn btn-primary btn-md"
					style="font-size: 17px" value="<%=RoleListCtl.OP_BACK%>">
			</div>
			<%
				}
			%>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">


		</form>

	</div>
</br>
</br>
</body>
<%@include file="Footer.jsp"%>

</body>
</html>
<%@page import="in.co.rays.project3.util.HTMLUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project3.model.RoleModelInt"%>
<%@page import="in.co.rays.project3.model.ModelFactory"%>
<%@page import="in.co.rays.project3.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project3.controller.UserListCtl"%>
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

<%@include file="Header.jsp" %>
	<div>
		<form class="pb-5" action="<%=ORSView.USER_LIST_CTL%>" method="post">
		<jsp:useBean id="dto" class="in.co.rays.project3.dto.UserDTO" scope="request"></jsp:useBean>
<%
List list1 = (List) request.getAttribute("roleList");
%>


<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;
int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
RoleModelInt rmodel = ModelFactory.getInstance().getRoleModel();
List list = ServletUtility.getList(request);
Iterator<UserDTO> it = list.iterator();
if (list.size() != 0) {
%>
<center>
<h1 class=" font-weight-bold pt-3"><u>User
							List</u></h1>
</center>

<br><br>

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

<div class="col-sm-2"></div>
<div class="col-sm-2" >
<input type="text" name="firstname" placeholder="Enter FirstName"
class="form-control"
value="<%=ServletUtility.getParameter("firstname", request)%>">
</div>&emsp;
<div class="col-sm-2">
<input type="text" name="loginid" placeholder="Enter Login Id"
class="form-control"
value="<%=ServletUtility.getParameter("loginid", request)%>">
</div>&emsp;
<div class="col-sm-2" ><%=HTMLUtility.getList("roleid", String.valueOf(dto.getRoleid()), list1)%></div>

<div class="col-sm-2">
<input type="submit" class="btn btn-primary btn-md"
style="font-size: 15px" name="operation"
value="<%=UserListCtl.OP_SEARCH%>">&emsp; <input
type="submit" class="btn btn-dark btn-md"
style="font-size: 15px" name="operation"
value="<%=UserListCtl.OP_RESET%>">
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
<th width="5%" class="text">S.NO</th>
<th width="15%" class="text">FirstName</th>
<th width="15%" class="text">LastName</th>
<th width="20%" class="text">LoginId</th>
<th width="10%" class="text">Gender</th>
<th width="10%" class="text">Role</th>
<th width="10%" class="text">DOB</th>
<th width="5%" class="text">Edit</th>
</tr>
</thead>
<%
	while (it.hasNext()) {
	dto = it.next();
	RoleDTO rbean = rmodel.findByPK(dto.getRoleid());
	%>
	<tbody>
<tr style="font-weight: bold;">
<td align="left"><input type="checkbox" class="checkbox"
name="ids" value="<%=dto.getId()%>"
<%if (dto.getRoleid() == RoleDTO.ADMIN) {%> <%="disabled"%>
<%}%>></td>
<td class="text"><%=index++%></td>
<td class="text"><%=dto.getFirstname()%></td>
<td class="text"><%=dto.getLastname()%></td>
<td class="text"><%=dto.getLoginid()%></td>
<td class="text"><%=dto.getGender()%></td>
<td class="text"><%=rbean.getName()%></td> 
<td class="text"><%=DataUtility.getDateString(dto.getDob())%></td>
<div class="input-group"><td class="text"><a href="UserCtl?id=<%=dto.getId()%>"
<%if (dto.getRoleid() == RoleDTO.ADMIN) {%>
 <%}%>><i class="fas fa-edit" grey-text" style="font-size: 1rem;">Edit</a></i>
 </td>
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
value="<%=UserListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>
></td>
<td><input type="submit" name="operation"
class="btn btn-warning btn-md" style="font-size: 17px"
value="<%=UserListCtl.OP_NEW%>"></td>
<td><input type="submit" name="operation"
class="btn btn-danger btn-md" style="font-size: 17px"
value="<%=UserListCtl.OP_DELETE%>"></td>

<td align="right"><input type="submit" name="operation"
class="btn btn-secondary btn-md" style="font-size: 17px"
style="padding: 5px;" value="<%=UserListCtl.OP_NEXT%>"<%=(nextPageSize != 0) ? "" : "disabled"%>
></td>
</tr>
<tr></tr>
</table>

<%
}
if (list.size() == 0) {
System.out.println("user list view list.size==0");
%>


<center>
<h1 class=" font-weight-bold pt-3"><u>User
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
<input type="submit" name="operation" class="btn btn-primary btn-md"
style="font-size: 17px" value="<%=UserListCtl.OP_BACK%>">
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



<%@include file="Footer.jsp"%>

</body>
</html>
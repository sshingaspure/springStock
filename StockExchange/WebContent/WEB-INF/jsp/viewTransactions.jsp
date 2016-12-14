<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h3>Transactions Details</h3>
	<hr size="4" color="gray" />
	<h2>${message}</h2>
	<table>
		<tr>
			<th>Sr. No.</th>
			<th>Company Name</th>
			<th>Share value at transaction</th>
			<th>Number of shares</th>
			<th>Transaction Type</th>
			<th>Transaction Status</th>
			<th>Transaction Message</th>
		</tr>
		<%! int counter=1; %>  
		<c:forEach items="${transactionsList}" var="list">
			
			<tr>
				<td><%= counter %></td>
				<td> <c:out value="${list.cmp_name}" /></td>
				<td> <c:out value="${list.share_value_at_tran}" /></td>
				<td> <c:out value="${list.number_of_shares}" /></td>
				<td> <c:out value="${list.tran_type}" /></td>
				<td> <c:out value="${list.tran_status}" /></td>
				<td> <c:out value="${list.tran_message}" /></td>
			</tr>
		<% counter++; %> 
		</c:forEach>
			<tr></tr>
			<tr></tr>
			<tr><td><a href="logout">Logout</a></td></tr>
	</table>

</body>
</html>
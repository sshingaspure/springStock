<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.util.List"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View stocks</title>
</head>
<body>
	<h3>Employee Details</h3>
	<hr size="4" color="gray" />
	<h2>${message}</h2>
	<table>

		<c:forEach items="${listShares}" var="list">
				<tr>
					<td>Company Name: <c:out value="${list.cmp_name}" /></td>
					<td>Shares Number: <c:out value="${list.share_num}" /></td>
				</tr>
		</c:forEach>

	</table>

</body>
</html>
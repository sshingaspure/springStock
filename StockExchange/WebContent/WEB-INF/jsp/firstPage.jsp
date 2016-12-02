<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Login Successful</title>
</head>
<body>

<c:if test="${empty customer}">
    <h1>You will have to login first</h1>
</c:if>

	<h1>${customer.customerName}</h1>
	<h2>${message}</h2>

	<a href="viewStock">Check Stock</a>
	<a href="buySellStock.jsp">Buy or Sell Stock</a>

</body>
</html>

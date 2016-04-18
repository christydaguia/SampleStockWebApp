<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock App</title>
</head>
<body>
	Sample Stock Application for Buying and Selling Stocks
	
	<p>Current Cash: <input type="text" size="30" align="right" name="currentBalance" disabled="disabled" value=<%=request.getAttribute("currentBalance")%>></p>
	
	<p>Company Name: ${name}</p>
	<p>Current Price: ${price}</p>
	
	<p>Share Holdings for ${name}: ${currentShares}</p>
	
	<input type="hidden" name="buttonName">
	
	<form action="ConnectAPI" method="post">
	<p>No. of shares to Transact: <input type="text" size="10" align="right" name="numShares"></p>
	<input type="submit" name="btnBuy" value="Buy"><input type="submit" name="btnSell" value="Sell">
	</form>	

</body>
</html>
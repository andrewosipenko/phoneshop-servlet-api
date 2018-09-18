<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="cart" type="com.es.phoneshop.model.Cart" scope="request"/>

<jsp:include page ="/WEB-INF/common/header.jsp"/>
<html>
<head>
    <title>Cart</title>
</head>
<body>
<p>
    Welcome to cart page!
</p>
<table>
<thead>
<tr>
    <td>ID</td>
    <td>Code</td>
    <td>Description</td>
    <td>Price</td>
    <td>Currency</td>
    <td>Stock</td>
    <td>Quantity</td>
</tr>
</thead>
<c:forEach var="cartItem" items="${cart.getCartItems()}">
    <tr>
        <td>${cartItem.product.id}</td>
        <td>${cartItem.product.code}</td>
        <td><a href="./products/${cartItem.product.id}">${cartItem.product.description}</a></td>
        <td>${cartItem.product.price}</td>
        <td>${cartItem.product.currency}</td>
        <td>${cartItem.product.stock}</td>
        <td>${cartItem.quantity}</td>
    </tr>
</c:forEach>
</table>
</body>
</html>
<jsp:include page ="/WEB-INF/common/footer.jsp"/>


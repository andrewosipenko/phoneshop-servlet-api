
<%@ page import="com.es.phoneshop.model.product.Product" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Phoneshop cart</title>
    <link href="http://fonts.googleapis.com/css?family=Lobster+Two" rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="<c:url value="/styles/main.css"/>">
    <title>Title</title>
</head>
<body class="product-list">
<h2>Cart</h2>
<table>
    <c:forEach var="cartItem" items="${sessionScope.cart.cartList}">
    <tr>
        <td>
            <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.product.imageUrl}">
        </td>
        <td><a href="<c:url value="/products/${cartItem.product.id}"/>">${cartItem.product.description}</a></td>
        <td class="price">
            <fmt:formatNumber value="${cartItem.product.price}" type="currency" currencySymbol="${cartItem.product.currency.symbol}"/>
        </td>
        <td>
            <input name="quantity" value = "${cartItem.quantity}" class = "number"/>
        </td>
    </tr>
    </c:forEach>
    <p>
    </p>
</table>
</body>
</html>

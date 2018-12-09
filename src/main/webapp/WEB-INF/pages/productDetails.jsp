<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 19.11.2018
  Time: 13:41
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="com.es.phoneshop.model.product.Product" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product Detail</title>
    <link href="http://fonts.googleapis.com/css?family=Lobster+Two" rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="<c:url value="/styles/main.css"/>">
</head>
<body class="product-list">
<main>
    <p>
        Welcome to Expert-Soft training!
    </p>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <tr>
            <td>
                <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>${product.description}</td>
            <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
        </tr>
    </table>
    <form method="post" action="<c:url value="/products/${product.id}"/>">
        <p><input type="search" name="quantity" value="${quantity}">
            <button type="submit" name="productId", value="${product.id}">add</button></p>
    </form>
    <p>${quantityAnswer}</p>
    <h3>Your cart</h3>
    <c:forEach var="cartItem" items="${sessionScope.cart.cartList}">
        <tr>
            <td>
                <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.product.imageUrl}">
            </td>
            <td><a href="<c:url value="/products/${cartItem.product.id}"/>">${cartItem.product.description}</a></td>
            <td>${cartItem.quantity}</td>
        </tr>
    </c:forEach>
</main>
</body>
</html>



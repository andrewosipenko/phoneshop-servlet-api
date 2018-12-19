
<%@ page import="com.es.phoneshop.model.product.Product" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link href="http://fonts.googleapis.com/css?family=Lobster+Two" rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="<c:url value="/styles/main.css"/>">
    <title>Title</title>
</head>
<body class="product-list">
<jsp:include page="header.jsp"/>
<h2>Cart</h2>
<form method="post" action="<c:url value="/cart"/>">
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
        <td>
            <button formaction="<c:url value = "cart/${cartItem.product.id}/delete" />">delete</button>
        </td>
    </tr>
    </c:forEach>
</table>
    <p> ${quantityAnswer}</p>
    <p><input type="submit" value="Update"></p>
    <p>${adress}</p>
    <form action="<c:url value="/checkout"/>">
        <input type="submit" value="Checkout">
    </form>
</form>
</body>
</html>

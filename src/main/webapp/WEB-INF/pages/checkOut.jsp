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
<main>
    <br>
    <form method="post" action="<c:url value="/checkout"/>">
        <table>
            <c:forEach var="cartItem" items="${sessionScope.cart.cartItemList}">
                <tr>
                    <td>
                        <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.product.imageUrl}">
                    </td>
                    <td><a href="<c:url value="/products/${cartItem.product.id}"/>">${cartItem.product.description}</a></td>
                    <td>${cartItem.quantity}</td>
                </tr>
            </c:forEach>
            <tr>
                <p>Cart: ${cart.totalPrice}</p>
            </tr>
        </table>
        <br>
        <table>
            <tr>
                <p>Name: </p><input type="search" name="firstName" value="${firstName}">
                <p>${name}</p>
            </tr>
            <tr>
                <p>Last Name: </p><input type="search" name="lastName" value="${lastName}">
                <p>${lastName}</p>
            </tr>
            <tr>
                <p>Phone Number : </p> <input type="search" name="phone" value="${phone}">
                <p>${phoneNumber}</p>
            </tr>
            <tr>
                <p>Delivery address: </p> <input type="search" name="address" value="${address}">
            </tr>
        </table>
        <p><input type="submit" value="Confirm"></p>
    </form>
</main>
</body>
</html>

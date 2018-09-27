<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="cart" type="com.es.phoneshop.model.Cart" scope="request"/>

<jsp:include page="/WEB-INF/common/header.jsp"/>

<fmt:setBundle basename="i18n.msg"/>
<fmt:setLocale value="en"/>
<html>
<head>
    <title>Cart</title>
</head>
<body>
<p>
    Welcome to cart page!
</p>
<form method="post">
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
        <c:forEach var="cartItems" items="${cart.cartItems}" varStatus="status">
            <tr>
                <td>${cartItems.product.id}</td>
                <td>${cartItems.product.code}</td>
                <td><a href="./products/${cartItems.product.id}">${cartItems.product.description}</a></td>
                <td>${cartItems.product.price}</td>
                <td>${cartItems.product.currency}</td>
                <td>${cartItems.product.stock}</td>
                ${status.index}
                <td>
                    <input type="hidden" name="productId" value="${cartItems.product.id}">
                    <input type="text" id="quantity${status.index}" name="quantity"
                           value="${quantities[status.index] != null ? quantities[status.index] : cartItems.quantity}">

                    <c:if test="${not empty errors && not empty errors[status.index]}">
                        <label for="quantity${status.index}">
                            <fmt:message key="${errors[status.index]}" />
                        </label>
                    </c:if>
                </td>
                <td>
                    <button type="submit" name="delete" value="${cartItems.product.id}">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" value="Update">
</form>
<form action="<c:url value="/checkout"/> ">
    <button>Checkout</button>
</form>
</body>
</html>
<jsp:include page="/WEB-INF/common/footer.jsp"/>


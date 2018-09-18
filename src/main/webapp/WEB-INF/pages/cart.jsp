<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="cart" type="com.es.phoneshop.model.Cart" scope="request"/>

<jsp:include page="/WEB-INF/common/header.jsp"/>
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
        <c:forEach var="cartItems" items="${cart.getCartItems()}">
            <tr>
                <td>${cartItems.product.id}</td>
                <td>${cartItems.product.code}</td>
                <td><a href="./products/${cartItems.product.id}">${cartItems.product.description}</a></td>
                <td>${cartItems.product.price}</td>
                <td>${cartItems.product.currency}</td>
                <td>${cartItems.product.stock}</td>
                <td>
                    <input type="hidden" name="productId" value="${cartItems.product.id}">
                    <input type="text" name="quantity" id="quantity${status.index}"
                           value="${cartItems.quantity}"
                           class="number">
                    <c:if test="${not empty errors[status.index]}">
                        <br>
                        <label for="quantity${status.index}" class="error">
                            <fmt:message key="${errors[status.index]}"/>
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
<%--<form method="post" action="<c:url value="/cart/delete"/>">
    <input type="hidden" name="productId">
</form>--%>
</body>
</html>
<jsp:include page="/WEB-INF/common/footer.jsp"/>


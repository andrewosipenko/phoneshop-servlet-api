<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.Cart" scope="request"/>
<html>
<head>
    <title>my cart</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <td>Id</td>
        <td>Code</td>
        <td>Description</td>
        <td>Price</td>
        <td>Currency</td>
    </tr>
    </thead>
    <c:forEach var="cartItem" items="${cart.cartItems}">
        <tr>
            <td>${cartItem.product.id}</td>
            <td>${cartItem.product.code}</td>
            <td>
                <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                        ${product.description}
                </a>
            </td>
            <td>${cartItem.product.price}</td>
            <td>${cartItem.product.currency}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
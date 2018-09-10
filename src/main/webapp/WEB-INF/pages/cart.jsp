<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="cart" type="com.es.phoneshop.model.Cart" scope="request"/>


<html>
    <head>
        <title>Cart</title>
    </head>

    <header>
        <jsp:include page="/WEB-INF/pages/header.jsp"/>
    </header>

    <body>
    <p align="center">Welcome to cart</p>
    <table>
        <thead>
        <tr>
            <td>Id</td>
            <td>Code</td>
            <td>Description</td>
            <td>Price</td>
            <td>Quantity</td>
        </tr>
        </thead>

        <c:forEach var="cartItem" items="${cart.cartItems}">
            <tr>
                <td>${cartItem.product.id}</td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${cartItem.product.code}</a>
                </td>
                <td>${cartItem.product.description}</td>
                <td>${cartItem.product.price} ${cartItem.product.currency}</td>
                <td>${cartItem.quantity}</td>
            </tr>
        </c:forEach>

    </table>
    </body>

    <footer>
        <jsp:include page="/WEB-INF/pages/footer.jsp"/>
    </footer>

</html>

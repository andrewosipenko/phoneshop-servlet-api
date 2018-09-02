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
            <td>Currency</td>
            <td>Quantity</td>
        </tr>
        </thead>

        <c:forEach var="cartItem" items="${cart.cartItems}">
            <tr>
                <td><c:out value="${cartItem.product.id}"/></td>
                <td>
                    <a href = "<c:url value = "/products/${cartItem.product.id}" />"><c:out value="${cartItem.product.code}"/></a>
                </td>
                <td><c:out value="${cartItem.product.description}"/></td>
                <td><c:out value="${cartItem.product.price}"/></td>
                <td><c:out value="${cartItem.product.currency}"/></td>
                <td><c:out value="${cartItem.quantity}"/></td>
            </tr>
        </c:forEach>

    </table>
    </body>

    <footer>
        <jsp:include page="/WEB-INF/pages/footer.jsp"/>
    </footer>

</html>

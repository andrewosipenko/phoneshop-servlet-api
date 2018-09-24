<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.Cart" scope="request"/>

<html>
<header>
    <jsp:include page="/WEB-INF/pages/header.jsp"/>
</header>

<head>
    <title>Checkout page</title>
    <div style="text-align: center;"></div>
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
        <td>Quantity</td>
    </tr>
    </thead>
    <c:forEach var="cartItem" items="${cart.cartItems}">
        <tr id="${cartItem.product.id}">
            <td><c:out value="${cartItem.product.id}"/></td>
            <td>
                <a href = "<c:url value = "/products/${cartItem.product.id}" />"><c:out value="${cartItem.product.code}"/></a>
            </td>
            <td><c:out value="${cartItem.product.description}"/></td>
            <td><fmt:formatNumber value = "${cartItem.product.price}"/></td>
            <td><c:out value="${cartItem.product.currency}"/></td>
            <td><fmt:formatNumber value = "${cartItem.quantity}"/></td>
        </tr>
    </c:forEach>
</table>
<div style="text-align: center">
    <form method="post">
        <div style="text-align: center;">Checkout Page</div>
        <div>
            <label>First Name</label>
            <input type="text" required="true" name="Name">
        </div>
        <div>
            <label>Address</label>
            <input type="text" required="true" name="Address">
        </div>
        <div>
            <label>Phone</label>
            <input type="text" required="true" name="Phone">
        </div>
        <input value="Make order" type="submit">
    </form>
</div>

</body>

<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</html>
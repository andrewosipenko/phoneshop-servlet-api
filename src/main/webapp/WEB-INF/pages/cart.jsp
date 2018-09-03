<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="cart" type="com.es.phoneshop.model.Cart" scope="request"/>

<jsp:include page ="/WEB-INF/common/header.jsp"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>
    Welcome to cart page!
</p>

table>
<thead>
<tr>
    <td>ID</td>
    <td>Code</td>
    <td>Description</td>
    <td>Price</td>
    <td>Currency</td>
    <td>Stock</td>
</tr>
</thead>
<c:forEach var="cartItem" items="${cart.cartItems}">
    <tr>
        <td>${product.id}</td>
        <td>${product.code}</td>
        <td>${product.description}</td>
        <td>${product.price}</td>
        <td>${product.currency}</td>
        <td>${product.stock}</td>
    </tr>
</c:forEach>
</table>
</body>
</html>
<jsp:include page ="/WEB-INF/common/footer.jsp"/>


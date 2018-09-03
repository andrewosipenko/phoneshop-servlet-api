<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:include page ="/WEB-INF/common/header.jsp"/>

<p>
    Hello from product list!
</p>
<table>
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
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.id}</td>
            <td>${product.code}</td>
            <td><a href="./products/${product.id}">${product.description}</a></td>
            <td>${product.price}</td>
            <td>${product.currency}</td>
            <td>${product.stock}</td>
        </tr>
    </c:forEach>
</table>

<jsp:include page ="/WEB-INF/common/footer.jsp"/>

<jsp:include page ="/WEB-INF/common/footer.jsp"/>
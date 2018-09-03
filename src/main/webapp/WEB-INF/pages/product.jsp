<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>

<jsp:include page ="/WEB-INF/common/header.jsp"/>

<html>
<head>
    <title>Product</title>
</head>
<body>
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
    <tr>
        <%-- <td>
           <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
         </td>--%>
        <td>${product.id}</td>
        <td>${product.code}</td>
        <td>${product.description}</td>
        <td>${product.price}</td>
        <td>${product.currency}</td>
        <td>${product.stock}</td>
    </tr>
</table>
</body>
</html>

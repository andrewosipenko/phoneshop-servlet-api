<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>
<html>
<head>
    <title>product ${product.code}</title>
</head>
<body>


<table>
    <tr>
        <td>ID</td>
        <td>${product.id}</td>
    </tr>
    <tr>
        <td>Code</td>
        <td>${product.code}</td>
    </tr>
    <tr>
        <td>Descr</td>
        <td>${product.description}</td>
    </tr>
    <tr>
        <td>Price</td>
        <td>${product.price} ${product.currency}</td>
    </tr>
    <tr>
        <td>Stock</td>
        <td>${product.stock}</td>
    </tr>
</table>

</body>
</html>

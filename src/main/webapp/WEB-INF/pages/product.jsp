<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>
<h2>Info</h2>

<thead>
    <tr>
        <td>Id</td>
        <td><c:out value="${product.id}"/></td>
    </tr><br>
    <tr>
        <td>Code</td>
        <td><c:out value="${product.code}"/></td>
    </tr><br>
    <tr>
        <td>Description</td>
        <td><c:out value="${product.description}"/></td>
    </tr><br>
    <tr>
        <td>Price</td>
        <td><c:out value="${product.price}"/></td>
    </tr><br>
    <tr>
        <td>Currency</td>
        <td><c:out value="${product.currency}"/></td>
    </tr><br>
    <tr>
        <td>Stock</td>
        <td><c:out value="${product.stock}"/></td>
    </tr><br>
</thead>

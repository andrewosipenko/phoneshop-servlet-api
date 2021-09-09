<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product details">
    <p>
        Product details page!
    </p>
    <table>
        <tr>
            <td>${product.description}</td>
            <td><img src="${product.imageUrl}" alt="Product image"></td>
        </tr>
        <tr>
            <td>price - ${product.price} ${product.currency}</td>
            <td>stock - ${product.stock}</td>
        </tr>
    </table>
</tags:master>
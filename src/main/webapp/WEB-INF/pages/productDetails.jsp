<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
    <table>
    <thead>
    <tr>
    <td>Image</td>
    <td>Description</td>
    <td class="price">Price</td>
    <td>Stock</td>
    </tr>
    </thead>
    <tr>
    <td><img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}"></td>
    <td>${product.description}</td>
    <td class="price"><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></td>
    <td>${product.stock}</td>
    </tr>
    </table>
    <c:url value="/products/${product.id}" var="thisPage"/>
</tags:master>


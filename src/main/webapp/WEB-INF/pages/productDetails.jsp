<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.domain.product.model.Product" scope="request"/>
<tags:master pageTitle="Product details">
    <img src="${product.imageUrl}" alt="product image">
    <table>
        <thead>
        <tr>
            <td>
                Description
            </td>
            <td>
                Code
            </td>
            <td class="price">
                Price
            </td>
            <td class="stock">
                Stock
            </td>
        </tr>
        </thead>
        <tr>
            <td>
                ${product.description}
            </td>
            <td>
                ${product.code}
            </td>
            <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
            <td class="stock">
                <fmt:formatNumber value="${product.stock}" type="number"/>
            </td>
        </tr>
    </table>
</tags:master>
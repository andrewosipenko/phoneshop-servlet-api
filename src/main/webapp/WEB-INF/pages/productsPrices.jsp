<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Price History">
    <p>
            ${product.description}
    </p>
    <table>
        <thead>
        <tr>
            <td>
                Start date
            </td>
            <td class="price">
                Price
            </td>
        </tr>
        </thead>
        <c:forEach var="productPrice" items="${product.prices}">
            <tr>
                <td>
                   ${productPrice.key}
                </td>
                <td class="price">
                    <fmt:formatNumber value="${productPrice.value}" type="currency" currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
    <p>
        <c:forEach var="product" items="${lastViewed}">
            <tr>
                <td>
                    <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <a href="products/${product.id}">${product.description}</a>
                </td>
                <td class="price">
                    <a href="products/prices/${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </p>
</tags:master>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
    <p>
        <jsp:include page="/cart/minicart"/>
        <c:if test="${not empty errorMessage}">
            <%@ include file="../fragments/miniCart.jsp" %>
        </c:if>
    </p>
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
            <td><img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>${product.description}</td>
            <td class="price"><fmt:formatNumber value="${product.price}" type="currency"
                                                currencySymbol="${product.currency.symbol}"/></td>
            <td>${product.stock}</td>
        </tr>
    </table>

    <c:url value="/products/${product.id}" var="thisPage"/>
    <form method="post" action="${thisPage}">
        <p>
            <c:if test="${not empty param.success}">
                <p class="class-color-green">Successfully added</p>
                <br>
            </c:if>
            <span>Quantity: </span>
            <input name="quantity" value="${empty quantity ? 1 : quantity}" class="quantity"/>
            <button type="submit">Add</button>
            <c:if test="${not empty errorMessage}">
                <br>
                <p class="class-color-red">${errorMessage}</p>
            </c:if>
        </p>
    </form>

    <tags:recentlyViewed products="${recentlyViewedProducts}"/>
</tags:master>

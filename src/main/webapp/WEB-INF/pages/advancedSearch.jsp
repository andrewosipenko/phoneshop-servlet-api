<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <form method="post">
        <label>Description<input name="description"></label>
        <br>
        <br>
        <label>Min price <input name="minPrice" type="number"></label>
        <br>
        <br>
        <label>Max price <input name="maxPrice" type="number"></label>
        <br>
        <br>
        <label>Min stock <input name="minStock" type="number"></label>'
        <br>
        <br>
        <label>Max stock <input name="maxStock" type="number"></label>'
        <br>
        <button formaction="<c:url value="/advancedSearch"/>" formmethod="post">Search</button>
    </form>
    <c:if test="${not empty products}">
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
            </td>
            <td class="price">
                Price
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <a href="products/${product.id}">${product.description}</a>
                </td>
                <td class="price">
                    <a href="products/priceHistory/${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
                </td>
            </tr>
        </c:forEach>
    </table>
    </c:if>
</tags:master>
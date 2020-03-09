<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form>
        <input name="query" value="${param.query}">
        <button>Search</button>
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
                <tags:sortLink sortField="description" sortOrder="asc"/>
                <tags:sortLink sortField="description" sortOrder="desc"/>
            </td>
            <td class="price">
                Price
                <tags:sortLink sortField="price" sortOrder="asc"/>
                <tags:sortLink sortField="price" sortOrder="desc"/>
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
    <table class="recently-viewed">
        <tr>
            <jsp:useBean id="recentlyViewed" scope="request" type="java.util.List"/>
            <c:forEach var="product" items="${recentlyViewed}">
                <td>
                    <img src="${product.imageUrl}">
                    <div>${product.description}</div>
                    <div>
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </div>
                </td>
            </c:forEach>
        </tr>
    </table>
</tags:master>
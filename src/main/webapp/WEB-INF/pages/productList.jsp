<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="recentView" type="java.util.Deque" scope="request"/>

<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form>
        <input name="query" value="${param.query}">
        <button>Search</button>
    </form>
    <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
                <tags:sortList sort="description" order="asc"/>
                <tags:sortList sort="description" order="desc"/>
            </td>
            <td class="price">
                Price
                <tags:sortList sort="price" order="asc"/>
                <tags:sortList sort="price" order="desc"/>
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
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                            ${product.description}
                    </a>
                </td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
    <h2>
        Recently Viewed
    </h2>
    <div class="recently-viewed">
        <c:forEach var="product" items="${recentView}">
            <div class="recently-viewed-tile">
            <img class="product-tile"
                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                <p>
                    ${product.description}
                </p>
            </a>
            <p class="price">
                <fmt:formatNumber value="${product.price}" type="currency"
                                  currencySymbol="${product.currency.symbol}"/>
            </p>
            </div>
        </c:forEach>
    </div>
</tags:master>
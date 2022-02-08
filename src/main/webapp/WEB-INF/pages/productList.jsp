<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="recentViewed" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form>
        <input name="query" value="${param.query}">
        <buttoms>Search</buttoms>
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
                <tags:sortLink sort="description" order="asc"></tags:sortLink>
                <tags:sortLink sort="description" order="desc"></tags:sortLink>
            </td>
            <td class="price">
                Price
                <tags:sortLink sort="price" order="asc"></tags:sortLink>
                <tags:sortLink sort="price" order="desc"></tags:sortLink>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="${product.imageUrl}">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                            ${product.description}
                    </a>
                </td>
                <td class="price">
                    <a href="${pageContext.servletContext.contextPath}/products/priceHistory?productId=${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <div class="container">
        <ul class="nav nav-pills" role="tablist">
            <c:forEach var="recentProduct" items="${recentViewed}">
                <li><a href="${pageContext.servletContext.contextPath}/products/${recentProduct.id}"
                       role="tab" data-toggle="pill">
                        ${recentProduct.description}
                    <p>
                        <img class="product-tile" src=${recentProduct.imageUrl}>
                    </p>
                </a></li>
            </c:forEach>
        </ul>
    </div>
</tags:master>
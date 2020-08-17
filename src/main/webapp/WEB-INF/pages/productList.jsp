<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>

<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>

    <c:if test="${not empty param.message}">
        <div class="success">
                ${param.message}
        </div>
    </c:if>

    <c:if test="${not empty param.error}">
        <div class="error">
            There were errors adding to cart
        </div>
    </c:if>

    <div>
        <form method="get">
            <input type="text" name="query" value="${param.query}">
            <button type="submit">Search</button>
        </form>
    </div>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td class="description">
                Description
                <tags:sortFields sort="description" order="asc"/>
                <tags:sortFields sort="description" order="desc"/>
            </td>
            <td>
                Quantity
            </td>
            <td class="price">
                Price
                <tags:sortFields sort="price" order="asc"/>
                <tags:sortFields sort="price" order="desc"/>
            </td>
            <td></td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}" varStatus="status">
            <tr>
                <td>
                    <img class="product-tile" src=${product.imageUrl}>
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                            ${product.description}
                    </a>
                </td>
                <td>
                    <fmt:formatNumber value="1" var="quantity"/>
                    <input form="addItemToCart/${product.id}"
                           class="quantity"
                           type="text"
                           name="quantity"
                           value="${products.get(status.index).id == param.productId
                           ? (not empty param.quantity ? param.quantity : 1)
                           : 1}"/>
                    <c:if test="${not empty param.error && products.get(status.index).id == param.productId}">
                        <div class="error">
                                ${param.error}
                        </div>
                    </c:if>
                    <input form="addItemToCart/${product.id}"
                           type="hidden"
                           name="productId"
                           value="${product.id}"/>
                    <input form="addItemToCart/${product.id}"
                           type="hidden"
                           name="redirect"
                           value="PLP"/>
                </td>
                <td class="price">
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}/priceHistory">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
                <td>
                    <button form="addItemToCart/${product.id}"
                            formaction="${pageContext.servletContext.contextPath}/add-product-to-cart/${product.id}">
                        Add to cart
                    </button>
                </td>
            </tr>
            <form id="addItemToCart/${product.id}" method="post"></form>
        </c:forEach>
    </table>
    <tags:recentlyViewed recentlyViewed="${recentlyViewed}"/>
</tags:master>
</form>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<script src="${pageContext.servletContext.contextPath}/js/main.js"></script>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<jsp:useBean id="recently_viewed" type="com.es.phoneshop.model.recentlyViewedProducts.RecentlyViewedProducts"
             scope="request"/>

<tags:master pageTitle="Product List">
    <h2>
        Welcome to Expert-Soft training!
    </h2>

    <c:if test="${not empty param.message}">
        <div class="success">
                ${param.message}
        </div>
    </c:if>
    <c:if test="${not empty param.error}">
        <div class="error">
            There was an error while adding to the cart
        </div>
    </c:if>
    <h1>
        Products
    </h1>
    <form>
        <input name="query" value="${param.query}">
        <button>Search</button>
    </form>
    <form method="post" action="${pageContext.servletContext.contextPath}/products/">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td class="description">
                    Description
                    <tags:sortLink sort="description" order="asc"/>
                    <tags:sortLink sort="description" order="desc"/>
                </td>
                <td>
                    Quantity
                </td>
                <td class="price">
                    Price
                    <tags:sortLink sort="price" order="asc"/>
                    <tags:sortLink sort="price" order="desc"/>
                </td>
                <td></td>
            </tr>
            </thead>
            <c:forEach var="product" items="${products}" varStatus="productIndex">
                <tr>
                    <td>
                        <img class="product-tile" src="${product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                                ${product.description}
                        </a>
                    </td>
                    <td class="quantity">
                        <input name="quantity${product.id}" value="${not empty param.error and param.errorProductId eq product.id ? param.errorQuantity : 1}">
                        <c:if test="${param.errorProductId eq product.id}">
                            <div class="error">
                                    ${param.error}
                            </div>
                        </c:if>
                        <input type="hidden" name="productId" value="${product.id}"/>
                    </td>
                    <td class="price">
                        <a href="javascript:void(0)" onmouseover="showPopup(${productIndex.index})"
                           onmouseleave="showPopup(${productIndex.index})">
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </a>
                        <div id="popup${productIndex.index}" class="popup" style="display:none">
                            <tags:productPriceHistoryPopup priceHistoryList="${product.priceHistoryList}"
                                                           productDescription="${product.description}"/>
                        </div>
                    </td>
                    <td>
                        <button id="addCartItem"
                                formaction="${pageContext.servletContext.contextPath}/products/addCartItem/${product.id}">
                            Add to cart
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
    <form id="addCartItem" method="post"></form>
    <div>
        <tags:recentlyViewedProducts recently_viewed="${recently_viewed}"/>
    </div>
</tags:master>

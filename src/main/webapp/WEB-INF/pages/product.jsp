<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="recentView" type="java.util.Deque" scope="request"/>

<tags:master pageTitle="Product Details">
    <a href="${pageContext.servletContext.contextPath}/products">
            <-Back to products list
    </a>
    <p>
            ${cart}
    </p>
    <div class="success">
       ${param.message}
    </div>
    <p>
        Code: ${product.code}
    </p>
    <p>
        Description: ${product.description}
    </p>
    <p>
        <a href="#priceHistory">
        Price: ${product.price} ${product.currency}
        </a>
    </p>
    <p>
        Stock: ${product.stock}
    </p>
    <p>
        <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
    </p>
    <form method="post">
        quantity
        <input name="quantity" value=${not empty error ? param.quantity : 1}>
        <button>AddToCart</button>
        <p class="error">
        ${error}
        </p>
    </form>
    <a href="#x" class="overlay" id="priceHistory"></a>
    <div class="popup">
        <H1>Price history</H1>
        <h2>${product.description}</h2>
        <table>
            <thead>
            <tr>
                <td>Start date</td>
                <td>Price</td>
            </tr>
            </thead>
            <c:forEach var="timestamp" items="${product.priceHistory}">
                <tr>
                    <td>
                            ${timestamp.date}
                    </td>
                    <td class="price">
                        <fmt:formatNumber value="${timestamp.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
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

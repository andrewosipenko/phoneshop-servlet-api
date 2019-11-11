<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Detail">
    <p>
        ${product.description}
    </p>

    <p>
        <c:choose>
            <c:when test="${not empty error}">
                <span class="message-red">Error adding to cart!</span>
            </c:when>
            <c:when test="${param.success}">
                <span class="message-green">Added to cart successfully!</span>
            </c:when>
        </c:choose>
    </p>
    <table>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td>Price</td>
        </tr>
        <tr>
            <td>
                <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>${product.description}</td>
            <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
        </tr>
    </table>
    <form method="post" action="${pageContext.servletContext.contextPath}/products/${product.id}">
        <p>
            <label>Quantity:</label>
            <input name="quantity" class="price" value="${not empty param.quantity ? param.quantity : 1}">
            <button>Add to cart</button>
        </p>
        <p>
            <c:if test="${not empty error}">
                <span style="color:red">${error}</span>
            </c:if>
        </p>
    </form>
    <c:if test="${not empty viewedProducts}">
        <h2>Viewed Products</h2>
    </c:if>
    <table>
        <tr>
            <c:forEach var="product" items="${viewedProducts.viewedProducts}">
                <td>
                    <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    <br>
                    <a href="products/${product.id}">${product.description}</a>
                    <br>
                    <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                </td>
            </c:forEach>
        </tr>
    </table>
</tags:master>
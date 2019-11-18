<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">
    <p>
        <c:choose>
            <c:when test="${not empty errorMap}">
                <span class="message-red">Error adding to cart!</span>
            </c:when>
            <c:when test="${param.success}">
                <span class="message-green">Added to cart successfully!</span>
            </c:when>
        </c:choose>
    </p>
    <c:choose>
        <c:when test="${not empty cart.cartItems}">
        <form method="post" action="${pageContext.servletContext.contextPath}/cart">
            <table>
                <tr>
                    <td>Image</td>
                    <td>Description</td>
                    <td>Price</td>
                    <td>Quantity</td>
                    <td>Action</td>
                </tr>
                <c:forEach var="item" items="${cart.cartItems}" varStatus="status">
                    <c:set var="product" value="${item.product}"/>
                <tr>
                    <td>
                        <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    </td>
                    <td>${product.description}</td>
                    <td class="price">
                        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                    </td>
                    <td>
                        <input name="quantity" value="${not empty errorMap[product]? paramValues.quantity[status.index] : item.quantity}"/>
                        <c:if test="${not empty errorMap[product]}">
                            <span class="message-red">${errorMap[product]}</span>
                        </c:if>
                        <input type="hidden" name="productId" value="${product.id}"/>
                    </td>
                    <td>
                        <button form="deleteCartItem" name="productId" value="${product.id}">Delete</button>
                    </td>
                </tr>
                </c:forEach>
            </table>
            <br>
            <button>Update</button>
        </form>
        </c:when>
        <c:otherwise>
            <p>
                Cart is empty!
            </p>
        </c:otherwise>
    </c:choose>

    <form id="deleteCartItem" action="${pageContext.servletContext.contextPath}/cart/deleteCartItem" method="post">
    </form>

</tags:master>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Anna
  Date: 13.12.2018
  Time: 0:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="session"/>

<tags:master pageTitle="Cart">
    <c:if test="${not empty param.message}">
        <p class="success">${param.message}</p>
    </c:if>

    <form method="post" action="${pageContext.servletContext.contextPath}/cart">
        <p>
            <button>Update cart</button>
        </p>
        <c:if test="${not empty quantityErrors}">
            <p class="error">Failed to update cart</p>
        </c:if>
    </form>

    <br>
    <table>
        <tr>
            <td>Image</td>
            <td>Code</td>
            <td>Description</td>
            <td class="price">Price</td>
            <td class="number">Quantity</td>
            <td></td>
        </tr>
        <c:forEach var="item" items="${cart.cartItems}">
            <tr>
                <td>
                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                </td>
                <td>${item.product.code}</td>
                <td>${item.product.description}</td>
                <td class="price"><fmt:formatNumber value="${item.product.price}"
                                                    type="currency"
                                                    currencySymbol="${item.product.currency.symbol}"/>
                </td>
                <td>
                    <input name="quantity"
                           value="${not empty quantityErrors[item.product.id] ? paramValues['quantity'][status.index]:item.quantity}"
                           class="number">
                    <input type="hidden" name="productId" value="${item.product.id}"/>
                    <c:if test="${not empty quantityError[item.product.id]}">
                        <p class="error">${quantityError[item.product.id]}</p>
                    </c:if>
                </td>
                <td>
                    <button formaction="<c:url value="/cart/delete/${item.product.id}"/>">
                        Delete
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>

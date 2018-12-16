<%--
  Created by IntelliJ IDEA.
  User: Liza Kurilo
  Date: 09.12.2018
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Cart">
    <jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>

    <c:url var="contextLinkCart" context="${pageContext.servletContext.contextPath}" value="/cart" />

    <c:if test="${not empty param.message}">
        <p class="success">${param.message}</p>
    </c:if>

    <form method="post" action="${contextLinkCart}">
        <p>
            <button>Update cart</button>
        </p>

    <c:if test="${not empty quantityErrors}">
        <p class="error">Failed to update cart</p>
    </c:if>

    <table>
        <tr>
            <td></td>
            <td>Code</td>
            <td>Description</td>
            <td class="number">Price</td>
            <td class="number">Quantity</td>
            <td></td>
        </tr>

        <c:forEach var="item" items="${cart.cartItems}">
            <tr>
            <td>
                <img class="product-title" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ex..${item.product.imageUrl}"></td>
            </td>
                <td>
                    ${item.product.code}
                <td>${item.product.description}</td>
                <td>  <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/> </td>
                <td>
                    <input name="quantity" value="${not empty quantityErrors[item.product.id] ? paramValues['quantity'][status.index] : item.quantity}" class="number">
                    <input type="hidden" name="productId" value="${item.product.id}">
                    ${quantityErrors[item.product.id]}
                    <c:if test="${not empty quantityErrors[item.product.id]}">
                        <p class="error">${quantityErrors[item.product.id]}</p>
                    </c:if>
                </td>
                <td>
                    <button formaction="${contextLinkCart}/${item.product.id}">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>

        <p>
            <button>Update cart</button>
        </p>

    </form>
</tags:master>
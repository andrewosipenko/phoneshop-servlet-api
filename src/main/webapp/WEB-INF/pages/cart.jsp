<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cartItems" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Cart">
    <c:if test="${not empty param.message && empty errors}">
        <p class="success">
                ${param.message}
        </p>
    </c:if>
    <c:if test="${not empty errors}">
        <p class="error">
            An error occurred while updating cart
        </p>
    </c:if>
    <p>
        Total quantity: ${cart.totalQuantity}
    </p>
    <form method="post">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td class="quantity">Quantity</td>
                <td class="price">Price</td>
                <td>Operation</td>
            </tr>
            </thead>
            <c:forEach var="cartItem" items="${cartItems}" varStatus="status">
                <tr>
                    <td>
                        <img class="product-tile" src="${cartItem.product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${cartItem.product.productId}">
                                ${cartItem.product.description}
                        </a>
                    </td>
                    <td class="quantity">
                        <fmt:formatNumber value="${cartItem.quantity}" var="quantity"/>
                        <c:set var="error" value="${errors[cartItem.product.productId]}"/>
                        <input name="quantity"
                               value="${not empty error ? paramValues['quantity'][status.index] : cartItem.quantity}"
                               class="quantity"/>
                        <c:if test="${not empty error}">
                            <p class="error">
                                    ${errors[cartItem.product.productId]}
                            </p>
                        </c:if>
                        <input name="productId" type="hidden" value="${cartItem.product.productId}"/>
                    </td>
                    <td class="price">
                        <a href="${pageContext.servletContext.contextPath}/history/${cartItem.product.productId}">
                            <fmt:formatNumber value="${cartItem.product.price}" type="currency"
                                              currencySymbol="${cartItem.product.currency.symbol}"/>
                        </a>
                    </td>
                    <td>
                        <button form="deleteCartItem"
                                formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${cartItem.product.productId}">
                            Delete
                        </button>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td></td>
                <td>Total cost</td>
                <td><fmt:formatNumber value="${cart.totalCost}" type="currency"
                                      currencySymbol="$"/></td>
            </tr>
        </table>
        <p>
            <c:if test="${not empty cartItems}">
                <button>Update</button>
            </c:if>
        </p>
    </form>
    <form id="deleteCartItem" method="post"></form>
</tags:master>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>

<tags:master pageTitle="Cart">
    <c:if test="${not empty paramValues.success}">
            <p class = "class-color-green">Successfully updated</p>
    </c:if>
    <c:if test="${not empty success}">
            <p class ="class-color-red">Updating error</p>
    </c:if>
    <form method="post">
        <c:if test ="${cart.totalQuantity!=0}">
        <table>
            <thead>
            <tr>
                <td>
                    Image
                </td>
                <td>
                    Description
                </td>
                <td>
                    Quantity
                </td>
                <td>
                    Price
                </td>
                <td>
                    Action
                </td>
            </tr>
            </thead>
            <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
                <tr>
                    <td>
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.product.imageUrl}">
                    </td>
                    <td>
                        <c:url value="/products/${cartItem.product.id}" var="productUrl"/>
                        <input name="productId" type="hidden" value="${cartItem.product.id}"/>
                        <a href="${productUrl}">${cartItem.product.description}</a>
                    </td>
                    <td>
                        <input name="quantity" class="quantity" value="${empty errors[status.index]
                                                                    ? cartItem.quantity
                                                                    : quantities[status.index]}"/>
                        <c:if test="${errors[status.index] ne null}">
                            <br>
                            <p class = "class-color-red">${errors[status.index]}</p>
                        </c:if>
                    </td>
                    <td class="price">
                        <fmt:formatNumber value="${cartItem.product.price}" type="currency"
                                          currencySymbol="${cartItem.product.currency.symbol}"/>
                    </td>
                    <td>
                        <c:url value="/cart/delete/${cartItem.product.id}" var="deleteUrl"/>
                        <button formaction="${deleteUrl}">Delete</button>
                    </td>
                </tr>
            </c:forEach>

            <tr>
                <td colspan="3" class="price">
                    Price:
                </td>
                <td>
                    <fmt:formatNumber value="${cart.totalPrice}" type="currency" currencySymbol="USD"/>
                </td>
            </tr>
        </table>
        <p>
            <c:url value="/cart" var="updateUrl"/>
            <button formaction="${updateUrl}">Update</button>
        </p>
        </c:if>
        <c:if test="${cart.totalQuantity == 0}">
            <p class = "class-color-black">Your cart is empthy!</p>
        </c:if>
    </form>
</tags:master>

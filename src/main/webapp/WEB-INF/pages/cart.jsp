<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<script src="${pageContext.servletContext.contextPath}/js/main.js"></script>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>

<tags:master pageTitle="Cart">
    <div>
        <h3>
            Cart
        </h3>
        <c:if test="${not empty param.message}">
            <div class="success">
                    ${param.message}
            </div>
        </c:if>
        <c:if test="${not empty errors}">
            <div class="error">
                There was an error while updating the cart
            </div>
        </c:if>
        <form method="post" action="${pageContext.servletContext.contextPath}/cart">
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
                        Price
                    </td>
                    <td>
                        Quantity
                    </td>
                    <td></td>
                </tr>
                </thead>
                <c:forEach var="cartItem" items="${cart.items}" varStatus="cartItemIndex">
                    <tr>
                        <td>
                            <img class="product-tile" src="${cartItem.product.imageUrl}">
                        </td>
                        <td>
                            <a href="${pageContext.servletContext.contextPath}/products/${cartItem.product.id}">
                                    ${cartItem.product.description}
                            </a>
                        </td>
                        <td class="price">
                            <a href="javascript:void(0)" onmouseover="showPopup(${cartItemIndex.index})"
                               onmouseleave="showPopup(${cartItemIndex.index})">
                                <fmt:formatNumber value="${cartItem.product.price}" type="currency"
                                                  currencySymbol="${cartItem.product.currency.symbol}"/>
                            </a>
                            <div id="popup${cartItemIndex.index}" class="popup" style="display:none">
                                <tags:productPriceHistoryPopup priceHistoryList="${cartItem.product.priceHistoryList}"
                                                               productDescription="${cartItem.product.description}"/>
                            </div>
                        </td>
                        <td class="quantity">
                            <fmt:formatNumber value="${cartItem.quantity}" var="quantity"/>
                            <c:set var="error" value="${errors[cartItem.product.id]}"/>
                            <input name="quantity" value="${not empty error ? paramValues['quantity'][cartItemIndex.index] : cartItem.quantity}" class="quantity"/>
                            <c:if test="${not empty error}">
                                <div class="error">
                                        ${errors[cartItem.product.id]}
                                </div>
                            </c:if>
                            <input type="hidden" name="productId" value="${cartItem.product.id}"/>
                        </td>
                        <td>
                            <button id="deleteCartItem"
                                    formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${cartItem.product.id}">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td>Total cost</td>
                    <td>
                        <fmt:formatNumber value="${cart.totalCost}" type="currency"
                                          currencySymbol="${cart.currency.symbol}"/>
                    </td>
                </tr>
            </table>
            <p>
                <button>Update</button>
            </p>
        </form>
        <form id="deleteCartItem" method="post"></form>
    </div>
</tags:master>

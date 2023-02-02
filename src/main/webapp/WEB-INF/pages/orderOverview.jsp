<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<script src="${pageContext.servletContext.contextPath}/js/main.js"></script>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>

<tags:master pageTitle="Order">
    <div>
        <h3>
            Order
        </h3>
        <form>
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
                </tr>
                </thead>
                <c:forEach var="cartItem" items="${order.items}" varStatus="cartItemIndex">
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
                            ${cartItem.quantity}
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td>Subtotal</td>
                    <td>
                        <fmt:formatNumber value="${order.subtotal}" type="currency"
                                          currencySymbol="${order.currency.symbol}"/>
                    </td>
                    <td>Delivery Cost</td>
                    <td>
                        <fmt:formatNumber value="${order.deliveryCost}" type="currency"
                                          currencySymbol="${order.currency.symbol}"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>Total Cost</td>
                    <td>
                        <fmt:formatNumber value="${order.totalCost}" type="currency"
                                          currencySymbol="${order.currency.symbol}"/>
                    </td>
                    <td></td>
                </tr>
            </table>
            <h2>
                Your details
            </h2>
            <table>
                </tr>
                <tr>
                    <td>First name</td>
                    <td>
                            ${order.firstName}
                    </td>
                </tr>
                </tr>
                <tr>
                    <td>Last name</td>
                    <td>
                            ${order.lastName}
                    </td>
                </tr>
                </tr>
                <tr>
                    <td>Phone</td>
                    <td>
                            ${order.phone}
                    </td>
                </tr>
                </tr>
                <tr>
                    <td>Delivery date</td>
                    <td>
                            ${order.deliveryDate}
                    </td>
                </tr>
                </tr>
                <tr>
                    <td>Delivery address</td>
                    <td>
                            ${order.deliveryAddress}
                    </td>
                </tr>
                </tr>
                <tr>
                    <td>Payment method</td>
                    <td>
                            ${order.paymentMethod}
                    </td>
                </tr>
            </table>
        </form>
    </div>
</tags:master>

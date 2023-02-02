<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<script src="${pageContext.servletContext.contextPath}/js/main.js"></script>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<jsp:useBean id="paymentMethods" scope="request" type="java.util.List"/>

<tags:master pageTitle="Order">
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
        <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
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
                <tags:orderFormRow name="firstName" label="First Name" order="${order}" errors="${errors}"/>
                <tags:orderFormRow name="lastName" label="Last Name" order="${order}" errors="${errors}"/>
                <tags:orderFormRow name="phone" label="Phone" order="${order}" errors="${errors}" placeholder="+375 XX XXX XX XX"/>

                <tr>
                    <td>Delivery Date<span style="color:red">*</span></td>
                    <td>
                        <c:set var="date" value="deliveryDate" />
                        <c:set var="error" value="${errors[date]}" />
                        <input type="date" name=${date} value="${param[date]}"/>
                        <c:if test="${not empty error}">
                            <div class="error">
                                    ${error}
                            </div>
                        </c:if>
                    </td>
                </tr>


                <tags:orderFormRow name="deliveryAddress" label="Delivery Address" order="${order}" errors="${errors}"/>

                <tr>
                    <td>Payment Method<span style="color:red">*</span></td>
                    <td>
                        <c:set var="method" value="paymentMethod" />
                        <c:set var="error" value="${errors[method]}" />
                        <select name="paymentMethod">
                            <option></option>
                            <c:forEach var="paymentMethod" items="${paymentMethods}">
                                <option>${paymentMethod}</option>
                            </c:forEach>
                        </select>
                        <c:if test="${not empty error}">
                            <div class="error">
                                    ${error}
                            </div>
                        </c:if>
                    </td>
                </tr>
            </table>
            <p>
                <button>Order</button>
            </p>
        </form>
    </div>
</tags:master>

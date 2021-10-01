<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.product.order.Order" scope="request"/>
<tags:master pageTitle="Overview">
    <script>
        function sendToHistoryPage(id) {
            window.open("${pageContext.servletContext.contextPath}/priceHistory/" + id,
                "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
        }

        function sendToPDP(id) {
            window.open("${pageContext.servletContext.contextPath}/products/" + id,
                "_self", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
        }
    </script>
    <p>
    <div class="main-name">Overview</div>
    <p class="success-message">Order successfully added. Great choice!!!</p>
    <div class="content">
        <c:if test="${not empty order.cartItems}">
            <section><p>
                <table class="light-green">
                    <tr>
                        <td>Image</td>
                        <td>Description</td>
                        <td>Quantity</td>
                        <td>Price</td>
                    </tr>
                    <c:forEach var="cartItem" items="${order.cartItems}" varStatus="status">
                        <tr>
                            <td><img class="product-tile" src="${cartItem.cartProduct.imageUrl}"
                                     alt="image of product"></td>
                            <td>
                                <a onclick="sendToPDP(${cartItem.cartProduct.id})">
                                        ${cartItem.cartProduct.description} <br>
                                </a>
                            </td>
                            <td>
                                    ${cartItem.quantity}
                            </td>
                            <td>
                                <a onclick="sendToHistoryPage(${cartItem.cartProduct.id})">
                                    <c:set var="currency" value="${cartItem.cartProduct.currency.symbol}"/>
                                    <fmt:formatNumber value="${cartItem.cartProduct.price}" type="currency"
                                                      currencySymbol="${cartItem.cartProduct.currency.symbol}"/>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </section>
        </c:if>
    </div>
    <div class="block-name">Delivery info</div>
    <p>Total quantity: ${order.totalQuantity}
    <p>Subtotal price: <fmt:formatNumber value="${order.subtotalPrice}" type="currency"
                                         currencySymbol="${currency}"/></p>
    <p>Delivery price: <fmt:formatNumber value="${order.deliveryPrice}" type="currency"
                                         currencySymbol="${currency}"/></p>
    <table class="light-green" style="width: 25%">
        <tr>
            <td>First name:</td>
            <td>${order.firstName}</td>
        </tr>
        <tr>
            <td>Last name:</td>
            <td>${order.lastName}</td>
        </tr>
        <tr>
            <td>Phone:</td>
            <td>${order.phone}</td>
        </tr>
        <tr>
            <td>Delivery date:</td>
            <td>${order.deliveryDate}</td>
        </tr>
        <tr>
            <td>Delivery address:</td>
            <td>${order.deliveryAddress}</td>
        </tr>
        <tr>
            <td>Payment method:</td>
            <td>${order.paymentMethod}</td>
        </tr>
    </table>
</tags:master>
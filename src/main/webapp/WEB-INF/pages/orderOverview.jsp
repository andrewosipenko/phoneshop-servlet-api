<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.order.Order" scope="request"/>
<tags:master pageTitle="Order Overview">
    <table>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td>Price</td>
            <td>Quantity</td>
        </tr>
        <c:forEach var="orderItem" items="${order.cartItems}" varStatus="status">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${orderItem.product.imageUrl}">
                </td>
                <td>${orderItem.product.description}</td>
                <td>
                    <fmt:formatNumber value="${orderItem.product.price}" type="currency"
                                      currencySymbol="${orderItem.product.currency.symbol}"/>
                </td>
                <td>
                        ${orderItem.quantity}
                </td>
            </tr>
        </c:forEach>
        </br>
        <tr>
            <td colspan="3">Subtotal</td>
            <td>${order.subtotal}</td>
        </tr>
        <tr>
            <td colspan="3">Delivery cost</td>
            <td>${order.deliveryCost}</td>
        </tr>
        <tr>
            <td colspan="3">Total cost</td>
            <td>${order.totalPrice}</td>
        </tr>
    </table>
    <br>
    <table class="table table-sm">
        <tr>
            <th>First Name:</th>
            <th>${order.orderCreateForm.firstName}</th>
        </tr>

        <tr>
            <th>Last Name:</th>
            <th>${order.orderCreateForm.lastName}</th>
        </tr>

        <tr>
            <th>Phone:</th>
            <th>${order.orderCreateForm.phone}</th>
        </tr>

        <tr>
            <th>Address:</th>
            <th>${order.orderCreateForm.address}</th>
        </tr>

        <tr>
            <th>Payment method:</th>
            <th> ${order.orderCreateForm.paymentMethod}</th>
        </tr>

        <tr>
            <th>Date:</th>
            <th>${order.orderCreateForm.date}</th>
        </tr>

    </table>
    <form id="deleteCartItem" action="${pageContext.servletContext.contextPath}/cart/deleteCartItem"
          method="post"></form>
</tags:master>
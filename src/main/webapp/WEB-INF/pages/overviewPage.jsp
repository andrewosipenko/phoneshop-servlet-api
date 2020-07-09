<%--
  Created by IntelliJ IDEA.
  User: Арсений Камадей
  Date: 08.07.2020
  Time: 12:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" class="com.es.phoneshop.model.Order" scope="request"/>
<tags:master pageTitle="overview">
    <table>
        <h1>Please Remember your secret Key. By your secret key you can show your order.</h1>
        <h2> Secret key: ${order.secureId}</h2>
        <thead>
        <td>Description</td>
        <td>Image</td>
        <td>Count</td>
        </thead>
        <c:forEach var="cartItem" items="${order.cartItems}">
            <tr>
                <td>${cartItem.product.description}</td>
                <td><img class="product-tile" src="${cartItem.product.imageUrl }"></td>
                <td>${cartItem.quantity}</td>
            </tr>
        </c:forEach>
    </table>
    <p>Name: ${order.customer.firstName} ${order.customer.lastName}</p>
    <p>Phone: ${order.customer.phoneNumber}</p>
    <p>Delivery date: ${order.deliveryDate.toString()}</p>
    <p>Delivery address: ${order.deliveryAddress}</p>
    <p>Cart subtotal: ${order.subtotalPrice}</p>
    <p>Delivery cost: ${order.deliveryCost}</p>
    <p>Total price: ${order.totalPrice}</p>
    <p>Payment method: ${order.paymentMethod.toString()}</p>
    <p style="font: bold">Secure Id(Secret key): ${order.secureId}</p>
</tags:master>
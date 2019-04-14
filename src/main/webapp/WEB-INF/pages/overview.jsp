<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.product.dao.order.Order" scope="request"/>
<tags:master pageTitle="Overview">
    <p>
    <h2>Thanks for purchase</h2>
    <p>
    <table>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td>Price</td>
            <td>Quantity</td>
        </tr>
        <c:forEach items="${order.cart.cartItems}" var="cartItem" varStatus="loop">
            <c:set var="product" value="${cartItem.product}"/>
            <tr>
                <td>
                    <img class="product-tile" src="${product.imageUrl}" alt="${product.code}">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                            ${product.description}
                    </a>
                </td>
                <td>$${product.price}</td>
                <td>
                        ${cartItem.quantity}
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td>Subtotal</td>
            <td colspan="2" align="right">$${order.cart.totalPrice}</td>
        </tr>
        <tr>
            <td>Delivery [${order.deliveryMode.name.toLowerCase()}]</td>
            <td colspan="2" align="right">$${order.deliveryMode.price}</td>
        </tr>
        <tr>
            <td>Total</td>
            <td colspan="2" align="right">$${order.cart.totalPrice + order.deliveryMode.price}</td>
        </tr>
    </table>
    <p>

        <form method="post">
    <p>
    <div>
        First name: ${order.firstName}
    </div>
    <p>
    <div>
        Last name: ${order.lastName}
    </div>
    <p>
    <div>
        Phone: ${order.phone}
    </div>
    <p>
    <div>
        Delivery mode: ${order.deliveryMode.name}
    </div>
    <p>
    <div>
        Delivery date: ${order.deliveryDate}
    </div>
    <p>
    <div>
        Delivery costs: $${order.deliveryMode.price}
    </div>
    <p>
    <div>
        Delivery address: ${order.deliveryAddress}
    </div>
    <p>
    <div>
        Payment method: ${order.paymentMethod.name}
    </div>

</tags:master>
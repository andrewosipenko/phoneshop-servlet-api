<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.Order" scope="request"/>
<tags:master pageTitle="Order overview">
    <h2>Order overview</h2>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td class="quantity">Quantity</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <c:forEach var="cartItem" items="${order.cartItems}" varStatus="status">
            <tr>
                <td>
                    <img class="product-tile" src="${cartItem.product.imageUrl}">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${cartItem.product.id}">
                            ${cartItem.product.description}
                    </a>
                </td>
                <td class="quantity">
                    <fmt:formatNumber value="${cartItem.quantity}" var="quantity"/>
                        ${cartItem.quantity}
                </td>
                <td class="price">
                    <a href="${pageContext.servletContext.contextPath}/history/${cartItem.product.id}">
                        <fmt:formatNumber value="${cartItem.product.price}" type="currency"
                                          currencySymbol="${cartItem.product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td></td>
            <td></td>
            <td>Subtotal</td>
            <td><fmt:formatNumber value="${order.subtotal}" type="currency"
                                  currencySymbol="$"/></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td>Delivery cost</td>
            <td><fmt:formatNumber value="${order.deliveryCost}" type="currency"
                                  currencySymbol="$"/></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td>Total cost</td>
            <td><fmt:formatNumber value="${order.totalCost}" type="currency"
                                  currencySymbol="$"/></td>
        </tr>
    </table>
    <h2>Your details</h2>
    <table>
        <tags:orderOverviewRow name="firstName" label="First Name" order="${order}"></tags:orderOverviewRow>
        <tags:orderOverviewRow name="lastName" label="Last Name" order="${order}"></tags:orderOverviewRow>
        <tags:orderOverviewRow name="phone" label="Phone" order="${order}"></tags:orderOverviewRow>
        <tags:orderOverviewRow name="deliveryAddress" label="Delivery Address" order="${order}"></tags:orderOverviewRow>
        <tags:orderOverviewRow name="deliveryDate" label="Delivery Date" order="${order}"></tags:orderOverviewRow>
        <tags:orderOverviewRow name="paymentMethod" label="Payment Method" order="${order}"></tags:orderOverviewRow>
    </table>
</tags:master>

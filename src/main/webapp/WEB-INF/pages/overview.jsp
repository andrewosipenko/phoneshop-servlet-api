<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page import="com.es.phoneshop.model.order.PaymentMethod" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Order overview">
    <h2>
        Thank you for you order!
    </h2>
    <c:choose>
        <c:when test="${not empty order.cartItems}">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th scope="col">Image</th>
                    <th scope="col">Description</th>
                    <th scope="col">Price</th>
                    <th scope="col">Quantity</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${order.cartItems}" varStatus="status">
                    <c:set var="product" value="${item.product}"/>
                    <tr>
                        <td>
                            <img class="product-tile"
                                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                        </td>
                        <td>${product.description}</td>
                        <td>
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </td>
                        <td>
                                ${item.quantity}
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="2">Subtotal</td>
                    <td>${order.subtotalCost}</td>
                </tr>
                <tr>
                    <td colspan="2">Delivery Cost</td>
                    <td>${order.deliveryCost}</td>
                </tr>
                <tr>
                    <td colspan="2">Total Cost</td>
                    <td>${order.totalCost}</td>
                </tr>
                </tfoot>
            </table>
            <form action="${pageContext.servletContext.contextPath}/checkout" method="post">
                <table>
                    <tr>
                        <td rowspan="3">Contact details</td>
                        <td>First name</td>
                        <td>
                                ${order.firstName}
                        </td>
                    </tr>
                    <tr>
                        <td>Last name</td>
                        <td>
                                ${order.lastName}
                        </td>
                    </tr>
                    <tr>
                        <td>Phone</td>
                        <td>
                                ${order.phone}
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="2">Delivery</td>
                        <td>Date</td>
                        <td>
                                ${order.deliveryDate}
                        </td>
                    </tr>
                    <tr>
                        <td>Address</td>
                        <td>
                                ${order.deliveryAddress}
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>Payment method</td>
                        <td>
                            <c:set var="MONEY" value="<%=PaymentMethod.MONEY%>"/>
                            <c:choose>
                                <c:when test="${order.paymentMethod == MONEY}">
                                    money
                                </c:when>
                                <c:otherwise>
                                    credit card
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </table>
            </form>
        </c:when>
        <c:otherwise>
            <p>
                Order is empty!
            </p>
        </c:otherwise>
    </c:choose>
</tags:master>
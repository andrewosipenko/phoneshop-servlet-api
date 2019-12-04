<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page import="com.es.phoneshop.model.order.PaymentMethod" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Checkout Page">
    <p>
        <c:if test="${not empty errorMap}">
            <span class="message-red">Error adding to orders!</span>
        </c:if>
    </p>
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
                        <tags:field label="First name" name="firstName" errorMap="${errorMap}"/>
                    </tr>
                    <tr>
                        <tags:field label="Last name" name="lastName" errorMap="${errorMap}"/>
                    </tr>
                    <tr>
                        <tags:phoneField label="Phone" name="phone" errorMap="${errorMap}"/>
                    </tr>
                    <tr>
                        <td rowspan="2">Delivery</td>
                        <tags:dateField label="date" name="deliveryDate" errorMap="${errorMap}"/>
                    </tr>
                    <tr>
                        <tags:field label="address" name="deliveryAddress" errorMap="${errorMap}"/>
                    </tr>
                    <tr>
                        <td></td>
                        <td>Payment method</td>
                        <td>
                            <c:set var="MONEY" value="<%=PaymentMethod.MONEY%>"/>
                            <c:choose>
                                <c:when test="${order.paymentMethod == MONEY}">
                                    <input type="radio" name="paymentMethod" value="money" checked>money<br>
                                    <input type="radio" name="paymentMethod" value="creditCard">credit card<br>
                                </c:when>
                                <c:otherwise>
                                    <input type="radio" name="paymentMethod" value="money">money<br>
                                    <input type="radio" name="paymentMethod" value="creditCard" checked>credit card<br>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </table>
                <br>
                <button class="btn btn-dark button-margin">Place to order</button>
                <br>
            </form>
        </c:when>
        <c:otherwise>
            <p>
                Order is empty!
            </p>
        </c:otherwise>
    </c:choose>
</tags:master>
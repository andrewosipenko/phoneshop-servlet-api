<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.order.Order" scope="request"/>
<tags:master pageTitle="Order">
    <c:if test="${not empty order.cartItems}">
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
        <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
            <table>
                <tags:field name="First name" errorMap="${errorMap}"></tags:field>
                <tags:field name="Last name" errorMap="${errorMap}"></tags:field>
                <tags:field name="Phone" errorMap="${errorMap}"></tags:field>
                <tags:field name="Address" errorMap="${errorMap}"></tags:field>
                <tr>
                    <th>Date:</th>
                    <th>
                        <input name="Date" type="date">
                        <c:if test="${not empty errorMap.get('Date')}">
                            <c:forEach var="error" items="${errorMap.get('Date')}">
                                <span style="color: red">${error}</span>
                            </c:forEach>
                        </c:if>
                    </th>
                </tr>
                <tr>
                    <th>Payment method:</th>
                    <th>
                        <select name="Payment method">
                            <option>Credit card</option>
                            <option>Money</option>
                        </select>
                    </th>
                </tr>
            </table>
            <br>
            <button>Place order</button>
        </form>

    </c:if>
    <c:if test="${empty order.cartItems}">
        <h1>Order is empty</h1>
    </c:if>
</tags:master>
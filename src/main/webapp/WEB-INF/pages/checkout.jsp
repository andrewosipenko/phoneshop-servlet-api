<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="httpCart" type="com.es.phoneshop.model.product.cart.Cart" scope="session"/>
<jsp:useBean id="deliveryMode" type="com.es.phoneshop.model.product.dao.order.DeliveryMode" scope="request"/>
<tags:master pageTitle="Checkout">
    <p>
    <table>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td>Price</td>
            <td>Quantity</td>
        </tr>
        <c:forEach items="${httpCart.cartItems}" var="cartItem" varStatus="loop">
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
            <td colspan="2" align="right">$${httpCart.totalPrice}</td>
        </tr>
        <tr>
            <td>Delivery [${deliveryMode.name.toLowerCase()}]</td>
            <td colspan="2" align="right">$${deliveryMode.price}</td>
        </tr>
        <tr>
            <td>Total</td>
            <td colspan="2" align="right">$${httpCart.totalPrice + deliveryMode.price}</td>
        </tr>
    </table>

    <p>
    <c:if test="${not empty requestScope['error']}">
        <h4>
            <span style="color: red">
                Error: please fill in all required fields
            </span>
        </h4>
    </c:if>

    <form method="post">
        <p>
        <div>
            <label for="firstName">First name </label>
            <input type="text" id="firstName" name="firstName" value="${param.firstName}" required>
        </div>
        <p>
        <div>
            <label for="lastName">Last name </label>
            <input type="text" id="lastName" name="lastName" value="${param.lastName}" required>
        </div>
        <p>
        <div>
            <label for="phone">Phone </label>
            <input type="tel" id="phone" name="phone" value="${param.phone}" required>
        </div>
        <p>
        <div>
            <label for="deliveryMode">Delivery mode </label>
            <select name="deliveryMode" id="deliveryMode" onchange="changeDeliveryMode.click()">
                <c:forEach var="mode" items="${requestScope['deliveryModes']}">
                    <option value="${mode}" ${deliveryMode eq mode ? "selected" : ""}>${mode.name}</option>
                </c:forEach>
            </select>
            <button id="changeDeliveryMode" formaction="checkout" formmethod="get" formnovalidate
                    style="display: none"></button>
        </div>
        <p>
        <div>
            <label for="deliveryDate">Delivery date </label>
            <input type="text" id="deliveryDate" name="deliveryDate" readonly value="Tomorrow">
        </div>
        <p>
        <div>Delivery costs: $${deliveryMode.price}</div>
        <p>
        <div>
            <label for="deliveryAddress">Delivery address </label>
            <input type="text" id="deliveryAddress" name="deliveryAddress" value="${param.deliveryAddress}" required>
        </div>
        <p>
        <div>
            <label for="paymentMethod">Payment method </label>
            <select name="paymentMethod" id="paymentMethod">
                <option>Money</option>
                <option>Credit card</option>
            </select>
        </div>
        <p>
            <button type="submit">Place order</button>
        </p>
    </form>

</tags:master>

<%--
  Created by IntelliJ IDEA.
  User: Арсений Камадей
  Date: 05.07.2020
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" class="com.es.phoneshop.model.Cart" scope="request"/>
<jsp:useBean id="errors" class="java.util.HashMap" scope="request"/>
<tags:master pageTitle="checkout">
    <table>
        <thead>
        <td>Description</td>
        <td>Image</td>
        <td>Count</td>
        </thead>
        <c:forEach var="cartItem" items="${cart.cartItems}">
            <tr>
                <td>${cartItem.product.description}</td>
                <td><img class="product-tile" src="${cartItem.product.imageUrl }"></td>
                <td>${cartItem.quantity}</td>
            </tr>
        </c:forEach>
    </table>
    Cart Subtotal: ${cart.price.toString()}
    <br/>
    Delivery Cost: ${deliveryCost}
    <h3>Total Price: ${cart.price.add(deliveryCost)}</h3>
    <form method="post">
        <table>
            <tr>
                <td>First name:</td>
                <td><input name="firstName">${firstName}</td>
                <td class="error-message" style="border-color: white">
                    <c:forEach var="error" items="${errors.get('firstName')}">
                        ${error}
                        <br/>
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <td>Second name:</td>
                <td><input name="secondName">${secondName}</td>
                <td class="error-message" style="border-color: white">
                    <c:forEach var="error" items="${errors.get('secondName')}">
                        ${error}
                        <br/>
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <td>Phone number:</td>
                <td><input name="phoneNumber">${phoneNumber}</td>
                <td class="error-message" style="border-color: white">
                    <c:forEach var="error" items="${errors.get('phoneNumber')}">
                        ${error}
                        <br/>
                    </c:forEach>
                </td class="error-message" style="border-color: white">
            </tr>
            <tr>
                <td>Delivery Date:</td>
                <td><input name="deliveryDate">${deliveryDate}</td>
                <td class="error-message" style="border-color: white">
                    <c:forEach var="error" items="${errors.get('deliveryDate')}">
                        ${error}
                    <br/>
                    </c:forEach>
            </tr>
            <tr>
                <td>Delivery Address:</td>
                <td><input name="deliveryAddress">${deliveryAddress}</td>
                <td class="error-message" style="border-color: white">
                    <c:forEach var="error" items="${errors.get('deliveryAddress')}">
                        ${error}
                    <br/>
                    </c:forEach>
            </tr>
            <tr>
                <td>Payment Method:</td>
                <td><input type="radio" name="paymentMethod" value="Credit cart"> credit cart
                    <input type="radio" name="paymentMethod" value="money"> money
                <td class="error-message" style="border-color: white">
                    <c:forEach var="error" items="${errors.get('paymentMethod')}">
                        ${error}
                    <br/>
                    </c:forEach>
            </tr>
        </table>
        <button>Place order</button>
    </form>

    <form action="products">
        <button>Back to List</button>
    </form>
</tags:master>
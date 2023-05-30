<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.Order" scope="request"/>
<tags:master pageTitle="Checkout">
    <c:if test="${not empty param.message && empty errors}">
        <p class="success">
                ${param.message}
        </p>
    </c:if>
    <c:if test="${not empty errors}">
        <p class="error">
            An error occurred while updating cart
        </p>
    </c:if>
    <p>
        Total quantity: ${cart.totalQuantity}
    </p>
    <form method="post">
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
            <tags:orderFormRow name="firstName" label="First Name" order="${order}" errors="${errors}"></tags:orderFormRow>
            <tags:orderFormRow name="lastName" label="Last Name" order="${order}" errors="${errors}"></tags:orderFormRow>
            <tags:orderFormRow name="phone" label="Phone" order="${order}" errors="${errors}"></tags:orderFormRow>
            <tags:orderFormRow name="deliveryAddress" label="Delivery Address" order="${order}" errors="${errors}"></tags:orderFormRow>
            <tags:orderFormRow name="deliveryDate" label="Delivery Date" order="${order}" errors="${errors}" placeholder="yyyy-MM-dd"></tags:orderFormRow>
            <tr>
                <td>Payment method<span style="color: red;">*</span></td>
                <td>
                    <select name="paymentMethod">
                        <option></option>
                        <c:forEach var="paymentMethod" items="${paymentMethods}">
                            <option value="${paymentMethod}"
                            <c:if test="${paymentMethod eq param['paymentMethod']}">
                                selected
                            </c:if>> ${paymentMethod}
                            </option>
                        </c:forEach>
                    </select>
                    <c:set var="error" value="${errors['paymentMethod']}"/>
                    <c:if test="${not empty error}">
                        <p class="error">
                                ${error}
                        </p>
                    </c:if>
                </td>
            </tr>
        </table>
        <p>
            <button>Place order</button>
        </p>
    </form>
</tags:master>

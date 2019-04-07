<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="httpCart" class="com.es.phoneshop.model.product.cart.Cart" scope="session"/>
<c:if test="${not empty httpCart.cartItems}">
    <p>
    <h2>
        <a href="${pageContext.servletContext.contextPath}/cart">Go To Cart</a>
    </h2>
    </p>
    <table>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td>Price</td>
            <td>Quantity</td>
        </tr>
        <c:forEach items="${httpCart.cartItems}" var="cartItem">
            <tr>
                <td>
                    <img class="product-tile" src="${cartItem.product.imageUrl}" alt="${cartItem.product.code}">
                </td>
                <td>${cartItem.product.description}</td>
                <td>$ ${cartItem.product.price}</td>
                <td>${cartItem.quantity}</td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="2">Total</td>
            <td>$${httpCart.totalPrice}</td>
        </tr>
    </table>
</c:if>

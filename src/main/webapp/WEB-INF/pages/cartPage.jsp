<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.product.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">
    <script>
        function sendToHistoryPage(id) {
            window.open("${pageContext.servletContext.contextPath}/priceHistory/" + id,
                "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
        }

        function sendToPDP(id) {
            window.open("${pageContext.servletContext.contextPath}/products/" + id,
                "_self", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
        }
    </script>
    <p>
    <div class="main-name">Cart</div>
    <c:choose>
        <c:when test="${not empty errorsMap}">
            <p class="error-message" style="font-size: large">
                Some problems with adding product to cart
            </p>
        </c:when>
        <c:when test="${not empty param.successMessage}">
            <p class="success-message" style="font-size: large">
                    ${param.successMessage}
            </p>
        </c:when>
    </c:choose>
    <div class="content">
        <c:if test="${not empty cart.cartItems}">
            <section><p>
                <form method="post" action="${pageContext.servletContext.contextPath}/cart">
                    <table class="light-green">
                        <tr>
                            <td>Image</td>
                            <td>Description</td>
                            <td>Quantity(${cart.totalQuantity})</td>
                            <td>Price(${cart.totalPrice})</td>
                        </tr>
                        <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
                            <tr>
                                <td><img class="product-tile" src="${cartItem.cartProduct.imageUrl}"
                                         alt="image of product"></td>
                                <td>
                                    <a onclick="sendToPDP(${cartItem.cartProduct.id})">
                                            ${cartItem.cartProduct.description} <br>
                                    </a>
                                </td>
                                <td>
                                    <input type="hidden" name="productId" value="${cartItem.cartProduct.id}">
                                    <c:set var="error" value="${errorsMap.get(cartItem.cartProduct.id)}"/>
                                    <input name="quantity" type="text"
                                           value="${not empty error ? paramValues.quantity[status.index] : cartItem.quantity}">
                                    <c:if test="${not empty error}">
                                        <div class="error-message">
                                                ${errorsMap.get(cartItem.cartProduct.id)}
                                        </div>
                                    </c:if>
                                </td>
                                <td>
                                    <a onclick="sendToHistoryPage(${cartItem.cartProduct.id})">
                                        <fmt:formatNumber value="${cartItem.cartProduct.price}" type="currency"
                                                          currencySymbol="${cartItem.cartProduct.currency.symbol}"/>
                                    </a>
                                </td>
                                <td>
                                    <button form="deleteForm"
                                            formaction="${pageContext.servletContext.contextPath}/deleteCartItem/${cartItem.cartProduct.id}">
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <p>
                        <button>Update</button>
                    </p>
                </form>
                <form id="deleteForm" method="post"></form>
                <form action="${pageContext.servletContext.contextPath}/checkout">
                    <button>Checkout</button>
                </form>
            </section>
        </c:if>
    </div>
</tags:master>
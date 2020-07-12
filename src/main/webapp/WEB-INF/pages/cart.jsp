<%--
  Created by IntelliJ IDEA.
  User: Арсений Камадей
  Date: 30.06.2020
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" class="com.es.phoneshop.model.Cart" scope="request"/>
<tags:master pageTitle="cart">
    <h1>Cart</h1>
    <c:if test="${empty cart.cartItems}">
        <h2>Cart is empty</h2>
        <form method="get" action="${pageContext.request.contextPath}/products">
            <button>
                Go to List
            </button>
        </form>
    </c:if>
    <c:if test="${not empty cart.cartItems}">
        <form method="post" action="${pageContext.request.contextPath}/cart">
            <table>
                <thead>
                <tr>
                    <td>Description</td>
                    <td>Image</td>
                    <td>Count</td>
                    <td>Price for 1 item</td>
                </tr>
                </thead>
                <c:forEach var="cartItem" items="${cart.cartItems}">
                    <c:set var="product" value="${cartItem.product}"/>
                    <input hidden name="productId" value="${product.id}">
                    <tr>
                        <td>${product.description}</td>
                        <td><img class="product-tile" src="${product.imageUrl}"></td>
                        <td><input type="text" name="quantity" value="${cartItem.quantity}"></td>
                        <td>${product.currentPrice.cost}</td>
                        <td style="border-color: white">

                            <button formmethod="post"
                                    formaction="${pageContext.request.contextPath}/cart/deleteCartItem/${cartItem.product.id}">
                                Delete
                            </button>

                        </td>
                        <td class="error-message" style="border-color: white">
                            <c:if test="${not empty requestScope.errors[product.id]}">
                                ${requestScope.errors[product.id]}
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <button>
                update
            </button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/checkout">
            <button>
                Go to checkout
            </button>
        </form>
    </c:if>
</tags:master>
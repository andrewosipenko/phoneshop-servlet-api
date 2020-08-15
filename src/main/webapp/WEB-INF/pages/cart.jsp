<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.entity.Cart" scope="request"/>

<tags:master pageTitle="Product Details">
    <div>
        <h3>
            Cart: ${cart}, total quantity: ${cart.totalQuantity}, total price: ${cart.totalCost}
        </h3>
    </div>
    <c:if test="${not empty param.message}">
        <div class="success">
                ${param.message}
        </div>
    </c:if>

    <c:if test="${not empty errors}">
        <div class="error">
                There were errors updating cart
        </div>
    </c:if>

    <form method="post" action="${pageContext.servletContext.contextPath}/cart">
        <div>
            <table>
                <thead>
                <tr>
                    <td>Image</td>
                    <td class="description">
                        Description
                    </td>
                    <td class="price">
                        Price
                    </td>
                    <td>
                        Quantity
                    </td>
                    <td></td>
                </tr>
                </thead>
                <c:forEach var="cartItem" items="${cart.items}" varStatus="status">
                    <tr>
                        <td>
                            <img class="product-tile" src=${cartItem.product.imageUrl}>
                        </td>
                        <td>
                            ${cartItem.product.description}
                        </td>
                        <td>
                            ${cartItem.product.price}
                        </td>
                        <td>
                            <fmt:formatNumber value="${cartItem.quantity}" var="quantity"/>
                            <c:set var="error" value="${errors[cartItem.product.id]}"/>
                            <input class="quantity" type="text" name="quantity" value="${not empty error ? paramValues['quantity'][status.index] : cartItem.quantity}"/>
                            <c:if test="${not empty error}">
                                <div class="error">
                                    ${error}
                                </div>
                            </c:if>
                            <input type="hidden" name="productId" value="${cartItem.product.id}"/>
                        </td>
                        <td>
                            <button form="deleteCartItem"
                                    formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${cartItem.product.id}">
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td></td>
                    <td></td>
                    <td>Total cost</td>
                    <td>${cart.totalCost}</td>
                    <td></td>
                </tr>
            </table>
        </div>
        <p>
            <button>
                Update
            </button>
        </p>
    </form>

    <form id="deleteCartItem" method="post"></form>

    <%--todo move into tag--%>
    <a href=${pageContext.servletContext.contextPath}/products>
        <p>
            return to product list
        </p>
    </a>
    <tags:recentlyViewed recentlyViewed="${recentlyViewed}"/>
</tags:master>

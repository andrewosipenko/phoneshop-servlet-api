<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="cart" type="com.es.phoneshop.model.product.cart.Cart" scope="request"/>
<tags:master pageTitle="Product details">
    <script>
        function myFunction(id) {
            window.open("${pageContext.servletContext.contextPath}/priceHistory/" + id,
                "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
        }
        function sendToPDP(id) {
            window.open("${pageContext.servletContext.contextPath}/products/" + id,
                "_self", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
        }
    </script>
    <p>
    <div style="font-size: xxx-large">${product.description}</div>
    <div class="content">
        <section class="cart"><p>
            <div class="block-name">
                Cart
            </div>
            <table>
                <c:forEach var="cartItem" items="${cart.cartItems}">
                    <tr>
                        <td>${cartItem.cartProduct.description}</td>
                        <td>${cartItem.quantity}</td>
                    </tr>
                </c:forEach>
            </table>
            <c:choose>
                <c:when test="${not empty errorMessage}">
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
        </section>
        <section class="info-product-table">
            <div class="block-name">
                About
            </div>
            <form method="post">
                <table class="light-green">
                    <tr>
                        <td colspan="2"><img src="${product.imageUrl}" alt="Product image"></td>
                    </tr>
                    <tr>
                        <td>Price</td>
                        <td>
                            <a onclick="myFunction(${product.id})">
                                <fmt:formatNumber value="${product.price}" type="currency"
                                                  currencySymbol="${product.currency.symbol}"/>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td>Stock</td>
                        <td>${product.stock}</td>
                    </tr>
                    <td>Cart</td>
                    <td>
                        <input name="quantity" value="${not empty param.quantity ? param.quantity : 1}">
                        <p>
                            <c:if test="${not empty errorMessage}">
                        <div class="error-message">
                                ${errorMessage}
                        </div>
                        </c:if>
                        <p>
                            <button>Add to cart</button>
                        </p>
                    </td>
                </table>
            </form>
        </section>
    </div>
    <div class="block-name">
        Recently viewed
    </div>
    <div class="recently-view-container">
        <c:forEach var="recentlyViewItem" items="${recentlyViewSection.recentlyView}">
            <div class="polaroid">
                <img src="${recentlyViewItem.imageUrl}" alt="Product image" class="mini-image-recently-view">
                <div class="container-polaroid">
                    <p>
                        <a onclick="sendToPDP(${recentlyViewItem.id})">
                                ${recentlyViewItem.description} <br>
                        </a>
                        <a onclick="myFunction(${recentlyViewItem.id})">
                            <fmt:formatNumber value="${recentlyViewItem.price}" type="currency"
                                              currencySymbol="${recentlyViewItem.currency.symbol}"/>
                        </a>
                    </p>
                </div>
            </div>
        </c:forEach>
    </div>
</tags:master>
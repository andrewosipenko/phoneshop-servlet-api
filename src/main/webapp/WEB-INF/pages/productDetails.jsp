<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.entity.Product" scope="request"/>

<tags:master pageTitle="Product Details">


    <c:if test="${not empty param.message}">
        <div class="success">
                ${param.message}
        </div>
    </c:if>
    <c:if test="${not empty param.error}">
        <div class="error">
                ${param.error}
        </div>
    </c:if>
    <p>
        Cart:
    </p>
    <p>
        <c:if test="${empty cart.getItems()}">
            Cart is empty yet
        </c:if>
    </p>
    <div>
        <table>
            <c:forEach var="cartItem" items="${cart.getItems()}">
                <tr>
                    <td>
                            ${cartItem.product.description}
                    </td>
                    <td>
                            ${cartItem.quantity}
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <p>
            ${product.description}
    </p>
    <form method="post" action="${pageContext.servletContext.contextPath}/add-product-to-cart/${product.id}">
        <table>
            <tr>
                <td>Image</td>
                <td>
                    <img src=${product.imageUrl}>
                </td>
            </tr>
            <tr>
                <td>code</td>
                <td>
                        ${product.code}
                </td>
            </tr>
            <tr>
                <td>price</td>
                <td class="price">
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}/priceHistory">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
            <tr>
                <td>stock</td>
                <td class="stock">
                        ${product.stock}
                </td>
            </tr>
            <tr>
                <td>quantity</td>
                <td>
                    <input class="quantity" type="text" name="quantity" value=${not empty param.quantity ? param.quantity : 1}>
                </td>
            </tr>
        </table>
        <p>
            <button type="submit">Add to cart</button>
        </p>
    </form>
    <a href=${pageContext.servletContext.contextPath}/products>
        <p>
            return to product list
        </p>
    </a>
</tags:master>
<tags:recentlyViewed recentlyViewed="${recentlyViewed}"/>
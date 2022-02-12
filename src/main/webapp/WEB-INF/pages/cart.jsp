<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>

<tags:master pageTitle="Cart">
    <a href="${pageContext.servletContext.contextPath}/products">
        <-Back to products list
    </a>
    <div class="success">
            ${param.message}
    </div>
    <form method="post" action="${pageContext.servletContext.contextPath}/cart">
        <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
            <thead>
            <tr>
                <td>Image</td>
                <td>
                    Description
                </td>
                <td>
                    quantity
                </td>
                <td class="price">
                    Price
                </td>
                <td></td>
            </tr>
            </thead>
            <c:forEach var="entry" items="${cart.items.entrySet()}">
                <tr>
                    <td>
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${entry.key.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${entry.key.id}">
                                ${entry.key.description}
                        </a>
                    </td>
                    <td>
                        <input name="quantity" value="${entry.value}" class="quantity">
                        <c:if test="${not empty errors[entry.key.id]}">
                            <div class="error">
                                    ${errors[entry.key.id]}
                            </div>
                        </c:if>
                        <input type="hidden" name="productId" value="${entry.key.id}">
                    </td>
                    <td class="price">
                        <fmt:formatNumber value="${entry.key.price}" type="currency"
                                          currencySymbol="${entry.key.currency.symbol}"/>
                    </td>
                    <td>
                        <button form="deleteCartItem"
                                formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${entry.key.id}">
                            Delete
                        </button>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td></td>
                <td>
                    Total cost
                </td>
                <td class="price">
                    <fmt:formatNumber value="${cart.totalCost}" type="currency"
                                      currencySymbol="USD"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    Total quantity
                </td>
                <td>
                    ${cart.totalQuantity}
                </td>
            </tr>
        </table>
        <p>
            <button>Update</button>
        </p>
    </form>
    <form id="deleteCartItem" method="post"></form>
</tags:master>

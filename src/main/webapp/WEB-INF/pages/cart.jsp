<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" class="com.es.phoneshop.model.product.entity.Cart" scope="session"/>
<tags:master pageTitle="Cart">
    <c:choose>
        <c:when test="${not empty inputErrors}">
            <p class="error">There were some problems updating the cart!</p>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty param.message}">
                <p class="success">${param.message}</p>
            </c:if>
        </c:otherwise>
    </c:choose>
    <h2>
        Total items: ${cart.totalItems}
    </h2>
    <form method="post">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>
                    Description
                    <tags:sortLink sort="DESCRIPTION" order="ASC"/>
                    <tags:sortLink sort="DESCRIPTION" order="DESC"/>
                </td>
                <td class="price">
                    Price
                    <tags:sortLink sort="PRICE" order="ASC"/>
                    <tags:sortLink sort="PRICE" order="DESC"/>
                </td>
                <td class="quantity">
                    Quantity
                </td>
                <td/>
            </tr>
            </thead>
            <c:forEach var="item" items="${cart.items}" varStatus="status">
                <tr>
                    <td>
                        <img class="product-tile"
                             src="${item.product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${item.product.id}">${item.product.description}</a>
                    </td>
                    <td class="price">
                        <a href=""
                           onclick='window.open("${pageContext.servletContext.contextPath}/products/${item.product.id}/priceHistory", "_blank", "scrollbars=0,resizable=0,height=400,width=360");'>
                            <fmt:formatNumber value="${item.product.price}" type="currency"
                                              currencySymbol="${item.product.currency.symbol}"/></a>
                    </td>
                    <td class="quantity">
                        <c:set var="inputError" value="${inputErrors[item.product.id]}"/>
                        <input name="quantity" class="quantity"
                               value="${not empty inputError? paramValues['quantity'][status.index]:item.quantity}"/>
                        <input type="hidden" name="productId" value="${item.product.id}"/>
                        <c:if test="${not empty inputError}">
                            <div class="error">${inputError}</div>
                        </c:if>
                    </td>
                    <td>
                        <button form="deleteForm"
                                formaction="${pageContext.servletContext.contextPath}/cart/deleteItem/${item.product.id}">
                            Delete
                        </button>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td/>
                <td/>
                <td>Total cost</td>
                <td class="price"><fmt:setLocale value="en_US"/>
                    <fmt:formatNumber value="${cart.totalCost}" type="currency"/></td>
                </td>
            </tr>
        </table>
        <p>
            <c:if test="${not empty cart.items}">
                <button>
                    Update
                </button>
            </c:if>
        </p>
    </form>
    <form action="${pageContext.servletContext.contextPath}/checkout">
        <button>
            Checkout
        </button>
    </form>
    <p>
        <tags:recentlyViewedProducts products="${recentProducts}"/>
    </p>
    <form id="deleteForm" method="post"></form>
</tags:master>

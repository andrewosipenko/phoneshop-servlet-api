<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.entity.Order" scope="request"/>
<tags:master pageTitle="Order overview">
    <p>
    </p>
    <h1>
        Order overview
    </h1>
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
            </tr>
            </thead>
            <c:forEach var="cartItem" items="${order.items}" varStatus="status">
                <tr>
                    <td>
                        <img class="product-tile" src=${cartItem.product.imageUrl}>
                    </td>
                    <td>
                            ${cartItem.product.description}
                    </td>
                    <td>
                        <fmt:formatNumber value="${cartItem.product.price}" type="currency"
                                          currencySymbol="${cartItem.product.currency.symbol}"/>
                    </td>
                    <td>
                        <fmt:formatNumber value="${cartItem.quantity}" var="quantity"/>
                            ${quantity}
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td></td>
                <td>
                    Subtotal
                </td>
                <td class="price">
                    <p>
                        <fmt:formatNumber value="${order.subtotal}" type="currency"
                                          currencySymbol="${order.currency.symbol}"/>
                    </p>
                </td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td>
                    Delivery cost
                </td>
                <td class="price">
                    <p>
                        <fmt:formatNumber value="${order.deliveryCost}" type="currency"
                                          currencySymbol="${order.currency.symbol}"/>
                    </p>
                </td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td>
                    Total cost
                </td>
                <td class="price">
                    <p>
                        <fmt:formatNumber value="${order.totalCost}" type="currency"
                                          currencySymbol="${order.currency.symbol}"/>
                    </p>
                </td>
            </tr>
        </table>
    </div>

    <h3>
        Your details
    </h3>
    <table>
        <tags:orderOverviewRow name="firstName" label="First name" order="${order}"/>
        <tags:orderOverviewRow name="lastName" label="Last name" order="${order}"/>
        <tags:orderOverviewRow name="phone" label="Phone" order="${order}"/>
        <tags:orderOverviewRow name="deliveryDate" label="Delivery date" order="${order}"/>
        <tags:orderOverviewRow name="deliveryAddress" label="Delivery address" order="${order}"/>
        <tr>
            <td>Payment method</td>
            <td>
               ${order.paymentMethod}
            </td>
        </tr>
    </table>

    <%--todo move into tag--%>
    <a href=${pageContext.servletContext.contextPath}/products>
        <p>
            return to product list
        </p>
    </a>
    <tags:recentlyViewed recentlyViewed="${recentlyViewed}"/>
</tags:master>

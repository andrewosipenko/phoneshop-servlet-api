<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" class="com.es.phoneshop.model.product.entity.Order" scope="request"/>
<tags:master pageTitle="Overview">
    <h1>
        Order overview
    </h1>
    <h2>
        Total items: ${order.totalItems}
    </h2>
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
        </tr>
        </thead>
        <c:forEach var="item" items="${order.items}" varStatus="status">
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
                        ${item.quantity}
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td/>
            <td/>
            <td>Subtotal cost:</td>
            <td class="price"><fmt:setLocale value="en_US"/>
                <fmt:formatNumber value="${order.subTotalCost}" type="currency"/></td>
            </td>
        </tr>
        <tr>
            <td/>
            <td/>
            <td>Delivery cost:</td>
            <td class="price"><fmt:setLocale value="en_US"/>
                <fmt:formatNumber value="${order.deliveryCost}" type="currency"/></td>
            </td>
        </tr>
        <tr>
            <td/>
            <td/>
            <td>Total cost:</td>
            <td class="price"><fmt:setLocale value="en_US"/>
                <fmt:formatNumber value="${order.totalCost}" type="currency"/></td>
            </td>
        </tr>
    </table>
    <h2>Your details:</h2>
    <table>
        <tr>
            <tags:orderOverviewStringForm order="${order}" name="firstName"
                                          label="First name:"></tags:orderOverviewStringForm>
        </tr>
        <tr>
            <tags:orderOverviewStringForm order="${order}" name="lastName"
                                          label="Last name:"></tags:orderOverviewStringForm>
        </tr>
        <tr>
            <tags:orderOverviewStringForm order="${order}" name="phoneNumber"
                                          label="Phone number:"></tags:orderOverviewStringForm>
        </tr>
        <tr>
            <tags:orderOverviewStringForm order="${order}" name="deliveryDate"
                                          label="Delivery date:"></tags:orderOverviewStringForm>
        </tr>
        <tr>
            <tags:orderOverviewStringForm order="${order}" name="deliveryAddress"
                                          label="Delivery address:"></tags:orderOverviewStringForm>
        </tr>
        <tr>
            <td>Payment method:</td>
            <td>
                    ${order.customerInfo.paymentMethod}
            </td>
        </tr>
    </table>
    <p>
        <tags:recentlyViewedProducts products="${recentProducts}"/>
    </p>
</tags:master>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" class="com.es.phoneshop.model.product.entity.Order" scope="request"/>
<tags:master pageTitle="Checkout">
    <h2>
        Total items: ${order.totalItems}
    </h2>
    <c:if test="${not empty possibleErrors}">
        <p class="error">There were some problems placing the order!</p>
    </c:if>
    <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
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
                <tags:orderStringForm order="${order}" name="firstName" label="First name:"
                                      possibleErrors="${possibleErrors}"></tags:orderStringForm>
            </tr>
            <tr>
                <tags:orderStringForm order="${order}" name="lastName" label="Last name:"
                                      possibleErrors="${possibleErrors}"></tags:orderStringForm>
            </tr>
            <tr>
                <tags:orderStringForm order="${order}" name="phoneNumber" label="Phone number:"
                                      possibleErrors="${possibleErrors}"></tags:orderStringForm>
            </tr>
            <tr>
                <tags:orderStringForm order="${order}" name="deliveryDate" label="Delivery date:"
                                      possibleErrors="${possibleErrors}"></tags:orderStringForm>
            </tr>
            <tr>
                <tags:orderStringForm order="${order}" name="deliveryAddress" label="Delivery address:"
                                      possibleErrors="${possibleErrors}"></tags:orderStringForm>
            </tr>
            <tr>
                <td>Payment method:<span style="color: darkred">*</span></td>
                <td>
                    <c:set var="inputError" value="${possibleErrors['paymentMethod']}"/>
                    <c:choose>
                        <c:when test="${not empty selectedPaymentMethod}">
                            <c:choose>
                                <c:when test="${selectedPaymentMethod == 'CASH'}">
                                    <input type="radio" name="paymentMethod" value="CASH" checked>Cash
                                    <input type="radio" name="paymentMethod" value="CREDIT_CARD">Card
                                </c:when>
                                <c:otherwise>
                                    <input type="radio" name="paymentMethod" value="CASH">Cash
                                    <input type="radio" name="paymentMethod" value="CREDIT_CARD" checked>Card
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <input type="radio" name="paymentMethod" value="CASH" checked>Cash
                            <input type="radio" name="paymentMethod" value="CREDIT_CARD">Card
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${not empty inputError}">
                        <div class="error">${inputError}</div>
                    </c:if>
                </td>
            </tr>
            </tr>
        </table>
        <p>
            <button>Place order</button>
        </p>
    </form>
    <p>
        <tags:recentlyViewedProducts products="${recentProducts}"/>
    </p>
    <form id="deleteForm" method="post"></form>
</tags:master>

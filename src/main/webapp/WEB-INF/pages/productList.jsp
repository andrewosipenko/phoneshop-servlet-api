<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <c:choose>
        <c:when test="${not empty sessionScope.inputError}">
            <p class="error">There were some problems adding item to the cart!</p>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty param.message}">
                <p class="success">${param.message}</p>
            </c:if>
        </c:otherwise>
    </c:choose>
    <form>
        <input name="query" value="${param.query}">
        <button>Search</button>
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
                <tags:sortLink sort="DESCRIPTION" order="ASC"/>
                <tags:sortLink sort="DESCRIPTION" order="DESC"/>
            </td>
            <td>
                Quantity
            </td>
            <td class="price">
                Price
                <tags:sortLink sort="PRICE" order="ASC"/>
                <tags:sortLink sort="PRICE" order="DESC"/>
            </td>
            <td/>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="${product.imageUrl}">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
                </td>
                <form method="post" action="${pageContext.servletContext.contextPath}/cart/addItem/${product.id}">
                    <td>
                        <c:set var="errorMessage" value="${sessionScope.inputError[product.id]}"/>
                        <input name="quantity" class="quantity" value="${empty errorMessage ? 1 : sessionScope.enteredQuantity}"/>
                        <c:if test="${not empty errorMessage}">
                            <span class="error">
                                ${errorMessage}
                            </span>
                        </c:if>
                    </td>
                    <td class="price">
                        <a href=""
                           onclick='window.open("${pageContext.servletContext.contextPath}/products/${product.id}/priceHistory", "_blank", "scrollbars=0,resizable=0,height=400,width=360");'>
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/></a>
                    </td>
                    <td>
                        <input type="submit" value="Add to cart"/>
                    </td>
                </form>
            </tr>
        </c:forEach>
    </table>
    <p>
        <tags:recentlyViewedProducts products="${recentProducts}"/>
    </p>
</tags:master>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product details">
    <p>
        Cart: ${cart}
    </p>
    <c:if test="${not empty param.message}">
        <div>
                ${param.message}
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div>
            There was an error adding to cart
        </div>
    </c:if>
    <p>
            ${product.description}
    </p>
    <form method="post">
        <table>
            <tr>
                <td>Image</td>
                <td>
                    <img src="${product.imageUrl}">
                </td>
            </tr>
            <tr>
                <td>code</td>
                <td>
                    <img src="${product.code}">
                </td>
            </tr>
            <tr>
                <td>stock</td>
                <td>
                    <img src="${product.stock}">
                </td>
            </tr>
            <tr>
                <td>price</td>
                <td>
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
            <tr>
                <td>quantity</td>
                <td>
                    <input name="quantity" value="${not empty param.quantity ? param.quantity : 1}">
                    <c:if test="${not empty error}">
                        <div>
                                ${error}
                        </div>
                    </c:if>
                </td>
            </tr>
        </table>
        <p>
            <button>Add to cart</button>
        </p>
    </form>

    <c:if test="${not empty recentViews}">
        <c:forEach var="view" items="${recentViews}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="${view.imageUrl}">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${view.id}"
                        ${view.description}</td>
            </tr>
        </c:forEach>
    </c:if>
</tags:master>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.core.dao.product.Product" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        <jsp:include page="../fragments/minicart.jsp"/>
    </p>
    <br>
    <div title="${product.code}">
        <img class="big-product-tile"
             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
    </div>
    <br>
    <table>
        <tr>
            <td>Description</td>
            <td>Price</td>
            <td>Stock</td>
        </tr>
        <tr>
            <td>${product.description}</td>
            <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency"
                                  currencySymbol="${product.currency.symbol}"/>
            </td>
            <td>${product.stock}</td>
        </tr>
    </table>
    <br>
    <form method="post">
        <input id="cartQuantity" name="quantity" value="${not empty param.quantity ? param.quantity : 1}">
        <button>Add to cart</button>

        <c:if test="${not empty requestScope['error']}">
            <span style="color:red">${requestScope['error']}</span>
        </c:if>

        <c:if test="${not empty requestScope['productAdded']}">
            <span style="color:green">${requestScope['productAdded']}</span>
        </c:if>
    </form>
    <hr>
    <p>
        <jsp:include page="../fragments/productReview.jsp"/>
    </p>
</tags:master>


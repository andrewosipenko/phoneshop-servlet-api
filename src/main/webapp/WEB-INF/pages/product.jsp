<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.entity.Product" scope="request"/>
<tags:master pageTitle="Product Details">
    <p>
            ${product.description}
    </p>
    <c:if test="${not empty param.message}">
        <p class="success">${param.message}</p>
    </c:if>
    <c:if test="${not empty param.error}">
        <p class="error">There was an error adding product to cart!</p>
    </c:if>
    <form method="post">
        <table>
            <tr>
                <td>Image</td>
                <td><img src="${product.imageUrl}"></td>
            </tr>
            <tr>
                <td>Code</td>
                <td>${product.code}</td>
            </tr>
            <tr>
                <td>Stock</td>
                <td>${product.stock}</td>
            </tr>
            <tr>
                <td>Price</td>
                <td class="price"><fmt:formatNumber value="${product.price}" type="currency"
                                                    currencySymbol="${product.currency.symbol}"/></td>
            </tr>
        </table>
        <p>
        <p>Quantity
            <input class="quantity" name="quantity" value="${not empty param.error ? param.quantity : 1}">
            <c:if test="${not empty param.error}">
                <span class="error">
                        ${param.error}
                </span>
            </c:if>
            <button>Add to cart</button>
        </p>
        </p>
    </form>
</tags:master>
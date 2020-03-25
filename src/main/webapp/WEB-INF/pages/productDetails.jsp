<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details">
    ${cart}
    <c:if test="${not empty error}">
        <p class="error">There was an error</p>
    </c:if>
    <c:if test="${not empty param.success}">
        <p class="success">Added to cart successfully</p>
    </c:if>
    <table>
        <thead>
        <tr>
            <td>Description</td>
            <td>
                    ${product.description}
            </td>
        </tr>
        <tr>
            <td>Stock</td>
            <td>
                    ${product.stock}
            </td>
        </tr>
        <tr>
            <td>Price</td>
            <td>
                <fmt:formatNumber value="${product.price}"/>
            </td>
        </tr>
        <tr>
            <td>Image</td>
            <td>
                <img src="${product.imageUrl}"/>
            </td>
        </tr>
        </thead>
    </table>
    <form method="post" action="${product.id}">
        <p>
            <label>
                <input name="quantity" value="${not empty param.quantity ? param.quantity : 1}" class="quantity">
            </label>
            <button>Add to cart</button>
            <c:if test="${not empty error}">
            <br>
        <p class="error">${error}</p>
        </c:if>
    </form>
</tags:master>
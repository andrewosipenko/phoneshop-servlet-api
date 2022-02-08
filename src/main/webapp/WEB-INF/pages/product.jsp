<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.bean.Product" scope="request"/>
<jsp:useBean id="recentViewed" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product details">
    <p>
            ${product.description}
    </p>
    <c:if test="${not empty param.message}">
        <div class="success">
                ${param.message}
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error">
            There was an error adding to cart
        </div>
    </c:if>
    <p>
        Cart : ${cart}
    </p>
    <form method="post">
        <table>
            <tr>
                <td>Image</td>
                <td>
                    <img src=${product.imageUrl}>
                </td>
            </tr>
            <td>Price</td>
            <td>
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
            </tr>
            </tr>
            <td>Code</td>
            <td>
                    ${product.code}
            </td>
            </tr>
            <td>Stock</td>
            <td>
                    ${product.stock}
            </td>
            </tr>
            <tr>
                <td>
                    quantity
                </td>
                <td>
                    <input name="quantity" value="${not empty error ? param.quantity : 1}" class="quantity">
                    <c:if test="${not empty error}">
                        <div class="error">
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

    <div class="container">
        <ul class="nav nav-pills" role="tablist">
            <c:forEach var="recentProduct" items="${recentViewed}">
                <li><a href="${pageContext.servletContext.contextPath}/products/${recentProduct.id}"
                       role="tab" data-toggle="pill">
                        ${recentProduct.description}
                    <p>
                        <img class="product-tile" src=${recentProduct.imageUrl}>
                    </p>
                </a></li>
            </c:forEach>
        </ul>
    </div>
</tags:master>

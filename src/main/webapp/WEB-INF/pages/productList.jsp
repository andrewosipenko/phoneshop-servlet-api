<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>

<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <div class="success">
            ${param.message}
    </div>
    <form>
        <label>
            <input name="query" value="${param.query}">
        </label>
        <button>Search</button>
    </form>
    <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
                <tags:sortList sort="description" order="asc"/>
                <tags:sortList sort="description" order="desc"/>
            </td>
            <td class="price">
                Price
                <tags:sortList sort="price" order="asc"/>
                <tags:sortList sort="price" order="desc"/>
            </td>
            <td>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}" alt="">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                            ${product.description}
                    </a>
                </td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
                <td>
                    <form method="post" action="${pageContext.servletContext.contextPath}/products">
                        <label>
                            <input name="quantity" class="quantity" value="${not empty param.error and param.productId eq product.id ? param.quantity : 1}"/>
                        </label>
                        <input type="hidden" name="productId" value="${product.id}"/>
                        <c:if test="${param.productId eq product.id}">
                        <div class="error">
                                ${param.error}
                        </div>
                    </c:if>
                    <button>
                        Add to cart
                    </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>

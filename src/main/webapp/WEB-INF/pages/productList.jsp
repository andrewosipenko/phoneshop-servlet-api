<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        <jsp:include page="/cart/minicart"/>
    </p>
    <form action="">
        <input name="search" value="${param.search}"/>
        <button type="submit">Search</button>
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
                <tags:sort search="${param.search}" sortBy="description"/>
            </td>
            <td class="price">
                Price
                <tags:sort search="${param.search}" sortBy="price"/>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <a href="products/${product.id}">${product.description}</a>
                </td>
                <td class="price">
                    <div>
                        <a href="#popup${product.id}">
                            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                        </a>
                    </div>
                    <div id="popup${product.id}" class="overlay">
                        <div class="popup">
                            <h2>Price history</h2>
                            <h1>${product.description}</h1>
                            <a class="close" href="#">&times;</a>
                            <div class="content">
                                <c:forEach var="historyOfPrices" items="${product.history}">
                                    <p>${historyOfPrices.value.date} the price was <fmt:formatNumber value="${historyOfPrices.key}" type="currency" currencySymbol="&#36"/></p>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form>
        <input name="searcher" id="searcher" value="${param.searcher}">
        <button>Find</button>
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description
                <a href="products?order=description&sort=asc&query =${param.searcher}">asc</a>
                <a href="products?order=description&sort=desc&query =${param.searcher}">desc</a></td>
            <td class="price">Price
                <a href="products?order=price&sort=asc&query =${param.searcher}">asc</a>
                <a href="products?order=price&sort=desc&query =${param.searcher}">desc</a></td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile" src="${product.imageUrl}">
                </td>
                <td>${product.description}</td>

                <td class="price">
                    <a href="products/price-history/${product.id}">
                            <fmt:formatNumber value="${product.getCurrentPrice().cost}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>

            </tr>
        </c:forEach>
    </table>
    <%--    <div class="b-container">--%>
    <%--        Sample Text--%>
    <%--    </div>--%>
    <%--    <div class="b-popup">--%>
    <%--        <div class="b-popup-content">--%>
    <%--            Text in Popup--%>
    <%--        </div>--%>
    <%--    </div>--%>
</tags:master>

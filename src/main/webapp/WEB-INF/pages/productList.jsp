<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageStyleClass="product-list">
    <br>
    <form>
        <input type="hidden" name="sort" value="${param.sort}"> <%--Now after searching sorting doesn't disappear --%>
        <input type="hidden" name="order" value="${param.order}">
        <input type = "search" name = "query" value="${param.query}">
        <button type = "submit">Search</button>
    </form>

    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description
                <tags:sortLink sort="description" order="asc" query="${param.query}" contentOftags="asc"/>
                <tags:sortLink sort="description" order="desc" query="${param.query}" contentOftags="desc"/>
            </td>
            <td class="price">Price
                <tags:sortLink sort="price" order="asc" query="${param.query}" contentOftags="asc"/>
                <tags:sortLink sort="price" order="desc" query="${param.query}" contentOftags="desc"/>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-title" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
                </td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <table>
        <form>
            <input name="searchText" value="${param.searchText}">
            <button>Search</button>
        </form>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
                <tags:sortLink sortField="description" sortOrder="asc"/>
                <tags:sortLink sortField="description" sortOrder="desc"/>
            </td>
            <td class="price">
                Price
                <tags:sortLink sortField="price" sortOrder="asc"/>
                <tags:sortLink sortField="price" sortOrder="desc"/>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/product/${product.id}">
                        <img class="product-tile" src="${product.imageUrl}" alt="product-title">
                    </a>
                </td>
                <td>${product.description}</td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.List" scope="request"/>

<tags:master pageTitle="AdvancedSearch">
    <p>
        Welcome to Expert-Soft training!
    </p>


    <p>
       Found ${products.size()} products
    </p>
    <div>
        <form method="get">
            <table>
                <tags:advancedSearchFormRow name="productCode" label="Product Code" error="${errors}"/>
                <tags:advancedSearchFormRow name="minPrice" label="Min price" error="${errors}"/>
                <tags:advancedSearchFormRow name="maxPrice" label="Max price" error="${errors}"/>
                <tags:advancedSearchFormRow name="minStock" label="Min stock" error="${errors}"/>
            </table>
            <p>
            </p>
            <button type="submit">Search</button>
        </form>
    </div>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td class="description">
                Description
                <tags:sortFields sort="description" order="asc"/>
                <tags:sortFields sort="description" order="desc"/>
            </td>
            <td class="price">
                Price
                <tags:sortFields sort="price" order="asc"/>
                <tags:sortFields sort="price" order="desc"/>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}" varStatus="status">
            <tr>
                <td>
                    <img class="product-tile" src=${product.imageUrl}>
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                            ${product.description}
                    </a>
                </td>
                <td class="price">
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}/priceHistory">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <tags:recentlyViewed recentlyViewed="${recentlyViewed}"/>
</tags:master>
</form>

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
                <tags:sortLink sortField="DESCRIPTION" sortOrder="ASCENDING">asc</tags:sortLink>
                <tags:sortLink sortField="DESCRIPTION" sortOrder="DESCENDING">desc</tags:sortLink>
            </td>
            <td class="price">
                Price
                <tags:sortLink sortField="PRICE" sortOrder="ASCENDING">asc</tags:sortLink>
                <tags:sortLink sortField="PRICE" sortOrder="DESCENDING">desc</tags:sortLink>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                        <img class="product-tile" src="${product.imageUrl}" alt="product-title">
                    </a>
                </td>
                <td>${product.description}</td>
                <td class="price">
                    <a onclick="myFunction(${product.id})">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                    <script>
                        function myFunction(id) {
                            window.open("${pageContext.servletContext.contextPath}/priceHistory/" + id,
                                "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
                        }
                    </script>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>
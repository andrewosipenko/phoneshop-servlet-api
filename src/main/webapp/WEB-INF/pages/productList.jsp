<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="recentlyViewSection" type="com.es.phoneshop.model.product.recentlyview.RecentlyViewSection"
             scope="request"/>
<tags:master pageTitle="Product List">
    <script>
        function sendToHistoryPage(id) {
            window.open("${pageContext.servletContext.contextPath}/priceHistory/" + id,
                "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
        }

        function sendToPDP(id) {
            window.open("${pageContext.servletContext.contextPath}/products/" + id,
                "_self", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
        }
    </script>
    <div class="block-name">
        Mobile phones
    </div>
    <c:choose>
        <c:when test="${not empty error}">
            <p class="error-message" style="font-size: large">
                Some problems with adding product to cart
            </p>
        </c:when>
        <c:when test="${not empty param.successMessage}">
            <p class="success-message" style="font-size: large">
                    ${param.successMessage}
            </p>
        </c:when>
    </c:choose>
    <table class="light-green">
        <form>
            <p>
                <label>
                    <input name="searchText" value="${param.searchText}">
                </label>
                <button>Search</button>
            </p>
        </form>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
                <tags:sortLink sortField="DESCRIPTION" sortOrder="ASCENDING">asc</tags:sortLink>
                <tags:sortLink sortField="DESCRIPTION" sortOrder="DESCENDING">desc</tags:sortLink>
            </td>
            <td>Quantity</td>
            <td class="price">
                Price
                <tags:sortLink sortField="PRICE" sortOrder="ASCENDING">asc</tags:sortLink>
                <tags:sortLink sortField="PRICE" sortOrder="DESCENDING">desc</tags:sortLink>
            </td>
            <td></td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}" varStatus="status">
            <form method="post" action="${pageContext.servletContext.contextPath}/products">
                <tr>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                            <img class="product-tile" src="${product.imageUrl}" alt="product-title">
                        </a>
                    </td>
                    <td>
                            ${product.description}
                    </td>
                    <td>
                        <input type="hidden" name="productId" value="${product.id}">
                        <input name="quantity" type="text"
                               value="${productIdWithError eq product.id and not empty error ? param.quantity : productsQuantitiesMap.get(product)}">
                        <c:if test="${productIdWithError eq product.id and not empty error}">
                            <div class="error-message">
                                    ${error}
                            </div>
                        </c:if>
                    </td>
                    <td class="price">
                        <a onclick="sendToHistoryPage(${product.id})">
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </a>
                    </td>
                    <td>
                        <button>Add to cart</button>
                        <br><br>
                    </td>
                </tr>
            </form>
        </c:forEach>
    </table>
    <c:if test="${not empty recentlyViewSection.recentlyView}">
        <div class="block-name">
            Recently viewed
        </div>
        <div class="recently-view-container">
            <c:forEach var="recentlyViewItem" items="${recentlyViewSection.recentlyView}">
                <div class="polaroid">
                    <img src="${recentlyViewItem.imageUrl}" alt="Product image" class="mini-image-recently-view">
                    <div class="container-polaroid">
                        <p>
                            <a onclick="sendToPDP(${recentlyViewItem.id})">
                                    ${recentlyViewItem.description} <br>
                            </a>
                            <a onclick="sendToHistoryPage(${recentlyViewItem.id})">
                                <fmt:formatNumber value="${recentlyViewItem.price}" type="currency"
                                                  currencySymbol="${recentlyViewItem.currency.symbol}"/>
                            </a>
                        </p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</tags:master>
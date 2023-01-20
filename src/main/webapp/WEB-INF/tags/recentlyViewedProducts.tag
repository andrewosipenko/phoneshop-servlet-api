<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="recently_viewed" type="com.es.phoneshop.model.recentlyViewedProducts.RecentlyViewedProducts"
              required="true" %>

<c:if test="${recently_viewed.products.size() > 0}">
    <div>
        <h3>
            Recently viewed products
        </h3>
        <table>
            <thead>
            <tr>
                <td>
                    Image
                </td>
                <td>
                    Description
                </td>
                <td>
                    Price
                </td>
            </tr>
            </thead>
            <c:forEach var="product" items="${recently_viewed.products}">
                <tr>
                    <td>
                        <img class="product-tile" src="${product.imageUrl}">
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
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>

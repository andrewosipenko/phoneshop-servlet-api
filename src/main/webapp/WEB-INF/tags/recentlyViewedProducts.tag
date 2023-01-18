<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="products" required="true" %>

<c:if test="${sessionScope.recentProducts!=null}">
    <h2>Recently viewed</h2>
</c:if>
<p>
<table>
    <tr>
        <c:forEach var="product" items="${sessionScope.recentProducts}">
            <td align="center">
                <img class="product-tile"
                     src="${product.imageUrl}"/>
                <p>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
                </p>
                <p class="centerAlign"><fmt:formatNumber value="${product.price}" type="currency"
                                                         currencySymbol="${product.currency.symbol}"/>
                </p>
            </td>
        </c:forEach>
    </tr>
</table>
</p>
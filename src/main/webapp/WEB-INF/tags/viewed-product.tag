<%--
  Created by IntelliJ IDEA.
  User: Арсений Камадей
  Date: 28.06.2020
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="viewedProducts" type="java.util.ArrayList  " scope="request"/>
<br>
<h1>Recently viewed</h1>
<br>
<table>
    <c:forEach var="product" items="${viewedProducts}">
        <td>
            <img src="${product.imageUrl}" class="product-tile">
            <br>
            <a href="${pageContext.request.contextPath}/products/${product.id}">${product.description}</a>
            <br>
            <fmt:formatNumber value="${product.getCurrentPrice().cost}" type="currency"
                              currencySymbol="${product.currency.symbol}"/>
        </td>
    </c:forEach>
</table>
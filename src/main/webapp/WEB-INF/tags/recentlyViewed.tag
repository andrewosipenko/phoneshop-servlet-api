<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="recentlyViewed" type="java.util.List" required="true" rtexprvalue="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Recently Viewed</title>
</head>
<body>
<h2>
    Recently Viewed
</h2>
<table>
    <thead>
    <tr>
        <c:forEach var="product" items="${recentlyViewed}">
            <td>
                <div>
                    <img class="product-tile" src=${product.imageUrl}>
                </div>
                <div>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                            ${product.description}
                    </a>
                </div>
                <div>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}/priceHistory">
                        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                    </a>
                </div>
            </td>
        </c:forEach>
    </tr>
    </thead>
</table>
</body>
</html>

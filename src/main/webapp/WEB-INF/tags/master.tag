<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>

<%@attribute name="pageStyleClass" required="false" type="java.lang.String" %>
<%@attribute name="pageTitle" required="true" type="java.lang.String" %>


<html>
    <head>
        <title>${pageTitle}</title>
        <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
    </head>

    <body class = "${pageStyleClass}">
        <div>
            <jsp:include page="/WEB-INF/pages/header.jsp"/>
        </div>

        <main>
            <form>
                <input type="button" value="Cart"
                       onClick='location.href="${pageContext.servletContext.contextPath}/cart"'>
            </form>

            <jsp:doBody/>

            <div class="viewedProduct">
                <strong>Recently viewed</strong>
            <table>
                <tr>
                    <c:forEach var="viewedProduct" items="${sessionScope.viewedProducts.viewedProducts}">
                        <td>
                            <p><img class="product-tile"
                                    src="${viewedProduct.imageUrl}">
                            </p>
                            <p><a href="${pageContext.servletContext.contextPath}/products/${viewedProduct.id}">${viewedProduct.description}</a></p>
                            <p>Price: <fmt:formatNumber value="${viewedProduct.price}"
                                                        type="currency"
                                                        currencySymbol="${viewedProduct.currency.symbol}"/> </p>
                        </td>
                    </c:forEach>
                </tr>
            </table>
            </div>
        </main>

        <div>
            <jsp:include page="/WEB-INF/pages/footer.jsp"/>
        </div>
    </body>
</html>

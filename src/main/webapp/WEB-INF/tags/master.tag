<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>

<%@attribute name="pageStyleClass" required="true" type="java.lang.String" %>

<html>
    <head>
        <title>Phone Shop</title>
        <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
    </head>

    <body class = "${pageStyleClass}">
        <div>
            <jsp:include page="/WEB-INF/pages/header.jsp"/>
        </div>

        <main>
            <jsp:doBody/>

            <table>
                <tr>
                    <c:forEach var="viewedProduct" items="${viewedProducts}">
                        <td>
                            <img class="product-title" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${viewedProduct.imageUrl}">
                            <p><a href="${pageContext.servletContext.contextPath}/products/${viewedProduct.id}">${viewedProduct.description}</a></p>
                            <p>Price: <fmt:formatNumber value="${viewedProduct.price}" type="currency" currencySymbol="${product.currency.symbol}"/> </p>
                        </td>
                    </c:forEach>
                </tr>
            </table>
        </main>

        <div>
            <jsp:include page="/WEB-INF/pages/footer.jsp"/>
        </div>
    </body>
</html>

<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="pageClass" required="false" type="java.lang.String" %>
<%@attribute name="pageTitle" required="true" type="java.lang.String" %>


<html>
    <head>
        <title>${pageTitle}</title>
        <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
    </head>

    <body class = "${pageClass}">
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
                <c:if test="${not empty sessionScope.viewedProducts}">
                    <br>
                    <strong>Recently viewed</strong>
                </c:if>
            <table>
                <tr>
                    <c:forEach var="viewedProduct" items="${sessionScope.viewedProducts.viewedProducts}">
                        <td>
                            <p><img class="product-tile"
                                    src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${viewedProduct.imageUrl}">
                            </p>
                            <a href="<c:url value="/products/${viewedProduct.id}"/>">${viewedProduct.description}</a>
                            <p><fmt:formatNumber value="${viewedProduct.price}" type="currency"
                                                 currencySymbol="${viewedProduct.currency.symbol}"/></p>
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

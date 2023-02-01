<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>


 <c:forEach var="viewedProduct" items="${viewedProducts}">
    <div >
            <div id = "viewedProducts">
                <div>
                <img class="product-tile" src="${viewedProduct.imageUrl}">
                </div>
                <a href="${pageContext.servletContext.contextPath}/products/${viewedProduct.id}">
                              ${viewedProduct.description}
                </a>
                <div>

                    <fmt:formatNumber value="${viewedProduct.price}" type="currency" currencySymbol="${viewedProduct.currency.symbol}"/>
                </div>
            </div>
    </div>
 </c:forEach>

</html>
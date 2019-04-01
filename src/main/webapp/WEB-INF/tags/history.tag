<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>

<c:if test="${not empty requestScope['history']}">
    <p>
    <h2>Recently viewed</h2>
    <c:forEach items="${requestScope['history']}" var="historyProduct" varStatus="loop">
        <div id="history-block">
            <div>
                <img align="center" class="product-tile" src="${historyProduct.imageUrl}"
                     alt="${historyProduct.code}">
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/products/${historyProduct.id}">${historyProduct.description}</a>
            </div>
            <div>${historyProduct.price}${historyProduct.currency.symbol}</div>
        </div>
    </c:forEach>
</c:if>

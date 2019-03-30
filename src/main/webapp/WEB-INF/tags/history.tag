<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="dispayedNumber" required="true" %>

<c:if test="${not empty applicationScope['history'].historyProducts}">
    <p>
    <h2>Recently viewed</h2>
    <c:forEach items="${applicationScope['history'].historyProducts}" var="historyProduct" varStatus="loop">
        <c:if test="${loop.index < dispayedNumber}">
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
        </c:if>
    </c:forEach>
</c:if>

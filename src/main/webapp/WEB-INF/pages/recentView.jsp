<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="recentView" type="java.util.Deque" scope="request"/>
<h2>
    Recently Viewed
</h2>
<div class="recently-viewed">
    <c:forEach var="product" items="${recentView}">
        <div class="recently-viewed-tile">
            <img class="product-tile"
                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                <p>
                        ${product.description}
                </p>
            </a>
            <p class="price">
                <fmt:formatNumber value="${product.price}" type="currency"
                                  currencySymbol="${product.currency.symbol}"/>
            </p>
        </div>
    </c:forEach>
</div>
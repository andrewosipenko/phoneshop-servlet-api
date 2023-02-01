<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" class="com.es.phoneshop.model.product.entity.Cart" scope="request"/>
<a href="${pageContext.servletContext.contextPath}/cart">
    Cart: ${cart.totalItems}
    <c:choose>
        <c:when test="${cart.totalItems == 1}">
            item
        </c:when>
        <c:otherwise>
            items
        </c:otherwise>
    </c:choose>
</a>

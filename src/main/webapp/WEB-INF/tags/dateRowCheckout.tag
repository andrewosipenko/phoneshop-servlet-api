<%@ tag trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="errorsMap" required="true" type="java.util.Map" %>
<%@ attribute name="order" required="true" type="com.es.phoneshop.model.product.order.Order" %>

<tr>
    <td>Delivery date:</td>
    <td>
        <c:set var="error" value="${errorsMap['deliveryDate']}"/>
        <c:choose>
            <c:when test="${not empty error}">
                <input type="date" name="deliveryDate" value="${param.deliveryDate}">
            </c:when>
            <c:otherwise>
                <input type="date" name="deliveryDate" value="${order.deliveryDate}">
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
    </td>
</tr>
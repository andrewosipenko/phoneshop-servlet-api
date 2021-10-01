<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="name" required="true" %>
<%@ attribute name="previousName" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="errors" required="true" type="java.util.Map" %>
<%@ attribute name="order" required="true" type="com.es.phoneshop.model.product.order.Order" %>
<tr>
    <td>
        ${label}:
    </td>
    <td>
        <c:set var="error" value="${errors[name]}"/>
        <input name="${name}" type="text" value="${not empty error ? param.name : previousName}">
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
    </td>
</tr>
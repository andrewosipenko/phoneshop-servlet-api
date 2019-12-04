<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="label" required="true" type="java.lang.String" %>
<%@ attribute name="name" required="true" type="java.lang.String" %>
<%@ attribute name="errorMap" required="true" type="java.util.Map" %>

<td>${label}</td>
<td>
    <input name="${name}" value="${param[name]}" placeholder="7-12 numbers"/>
    <c:if test="${not empty errorMap[name]}">
        <span class="message-red">${errorMap[name]}</span>
    </c:if>
</td>
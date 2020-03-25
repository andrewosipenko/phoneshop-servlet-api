<
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="errorMap" required="true" type="java.util.Map" %>

<tr>
    <th>${name}:</th>
    <th>
        <input name="${name}" value="${param[name]}">
        <c:if test="${not empty errorMap.get(name)}">
            <c:forEach var="error" items="${errorMap.get(name)}">
                <span style="color: red">${error}</span>
            </c:forEach>
        </c:if>
    </th>
</tr>
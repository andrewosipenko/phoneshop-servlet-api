<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="error" required="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr>
    <td>${label}</td>
    <td>
        <c:set var="error" value="${errors[name]}"/>
        <input name="${name}" value="${not empty param[name] ? param[name] : ""}">
        <c:if test="${not empty errors}">
            <div class="error">
                    ${error}
            </div>
        </c:if>
    </td>
</tr>

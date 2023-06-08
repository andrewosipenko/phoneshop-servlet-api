<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="errors" required="true" type="java.util.Map" %>

<tr>
    <td>${label}</td>
    <td>
        <c:set var="error" value="${errors[name]}"/>
        <input name=${name} value="${not empty error ? param[name] : product[name]}"/>
        <c:if test="${not empty error}">
            <p class="error">
                    ${error}
            </p>
        </c:if>
    </td>
</tr>
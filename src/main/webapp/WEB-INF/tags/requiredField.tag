<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="parameter" required="true" %>

<c:if test="${empty parameter}">
            <span style="color: red">
                   Required field
            </span>
</c:if>
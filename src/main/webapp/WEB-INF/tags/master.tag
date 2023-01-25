<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<tags:header pageTitle="${pageTitle}"/>
<main>
    <jsp:doBody/>
</main>
<tags:footer/>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page isErrorPage="true" %>

<tags:master pageTitle="Error">
    <h1>
        Sorry, an error occured
    </h1>
    <h1>${pageContext.errorData.throwable.message}</h1>
    <p>
        An unexpected error!
    </p>
</tags:master>
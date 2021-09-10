<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product not found">
    <h1>Error 404</h1>
    <h2>Product with code ${pageContext.exception.message} is not found</h2>
</tags:master>
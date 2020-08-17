<%@ page isErrorPage="true" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
<head>
    <title>
        Product not found error
    </title>
</head>
<body>
<h1>
    404
</h1>
<h2>
    Product with id ${pageContext.exception.message} not found
</h2>
<a href=${pageContext.servletContext.contextPath}/products>
    <p>
        return to product list
    </p>
</a>
</body>
</tags:master>
<%@ page isErrorPage="true" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
    <head>
        <title>
            Something went wrong
        </title>
    </head>
    <body>
    <h1>
        We are sorry
    </h1>
    <h2>
        And fix it soon. Probably
    </h2>
    <a href=${pageContext.servletContext.contextPath}/products>
        <p>
            return to product list
        </p>
    </a>
    </body>
</tags:master>
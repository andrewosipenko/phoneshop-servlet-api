<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>

<%@attribute name="pageStyleClass" required="true" type="java.lang.String" %>

<html>
    <head>
        <title>Phone Shop</title>
        <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
    </head>

    <body class = "${pageStyleClass}">
        <div>
            <jsp:include page="/WEB-INF/pages/header.jsp"/>
        </div>

        <main>
            <jsp:doBody/>
        </main>

        <div>
            <jsp:include page="/WEB-INF/pages/footer.jsp"/>
        </div>
    </body>
</html>

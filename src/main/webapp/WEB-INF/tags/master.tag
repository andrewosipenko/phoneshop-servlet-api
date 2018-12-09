<%@attribute name="pageTitle" type="java.lang.String" required="true" %>
<%@attribute name="pageClass" type ="java.lang.String" required="false" %>
<html>
<head>
    <tittle>${PageTitle}</tittle>
    <link href="http://fonts.googleapis.com/css?family=Lobster+Two" rel="stylesheet" type = "text/css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="${pageClass}">
<header>
    <c:url var="contexLink" contex = "${pageContext.servletContext.contextPath}" value = "/products"/>
    <a href = "${contexLink}">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
    </a>
</header>
<main>
    <jsp:doBody/>
</main>
<div>
</div>
</body>
</html>
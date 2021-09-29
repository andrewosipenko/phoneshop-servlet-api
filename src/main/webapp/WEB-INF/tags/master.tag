<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
    <title>${pageTitle}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
<header>
    <a class="main-logo" href="${pageContext.servletContext.contextPath}">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg" alt="logo"/>
        PhoneShop
    </a>`
    <a class="mini-cart" style="font-size: x-large" href="${pageContext.servletContext.contextPath}/cart">
        <jsp:include page="/miniCart"/>
    </a>
</header>
<main>
    <jsp:doBody/>
</main>
<p>Expert Soft, Inc. 2021.</p>
</body>
</html>
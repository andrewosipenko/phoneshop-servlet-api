<%
    String message = pageContext.getException().getMessage();
    String exception = pageContext.getException().getClass().toString();
%>
<html>
<head>
    <title>Product not find</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
<header>
    <a href="${pageContext.servletContext.contextPath}">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg" alt="logo"/>
        PhoneShop
    </a>
</header>
<main>
    <h1><strong>We have problem here :(</strong></h1>
    <h1><strong><%= message%></strong></h1>
    <p>Type of exception: <%= exception%></p>
</main>
<p>Expert Soft, Inc. 2021.</p>
</body>
</html>
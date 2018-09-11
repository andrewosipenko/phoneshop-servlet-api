<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<style>
    <%@include file="/WEB-INF/style/headerStyle.css"%>
</style>
<head>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/headerStyle.css">

</head>

<header class="header">
    <img class="ikon" src="http://image.flaticon.com/icons/png/128/25/25377.png"/>
    <h1 class="title">
        <a class="nativeLink" href="http://localhost:8080/phoneshop-servlet-api/products">Phoneshop</a>
    </h1>
    <a href="${pageContext.servletContext.contextPath}/cart"><img class="cartIkon"
                                                                  src="http://image.flaticon.com/icons/png/128/9/9278.png"/></a>
</header>
</html>

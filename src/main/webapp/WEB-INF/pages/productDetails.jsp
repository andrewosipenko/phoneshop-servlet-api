<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 19.11.2018
  Time: 13:41
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="com.es.phoneshop.model.product.Product" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product Detail</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css"></head>
<body class="product-list">
<header>
    <a href="${pageContext.servletContext.contextPath}">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
        PhoneShop
    </a>
</header>
<main>
    <p>
        Welcome to Expert-Soft training!
    </p>

    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <tr>
            <td>
                <img class="product-tile" src="<%="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/" + ((Product)request.getAttribute("product")).getImageUrl()%>">
            </td>
            <td><%=((Product)request.getAttribute("product")).getDescription()%></td>
            <td class="price">
                <%=((Product)request.getAttribute("product")).getPrice()%><%=((Product)request.getAttribute("product")).getCurrency()%>
            </td>
        </tr>
    </table>
</main>
</body>
</html>


</body>
</html>

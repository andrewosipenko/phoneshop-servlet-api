
<%--
  Created by IntelliJ IDEA.
  User: Liza Kurilo
  Date: 16.12.2018
  Time: 1:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <c:url var="contextLink" context="${pageContext.servletContext.contextPath}" value="/products" />
    <c:url var="contextLinkCart" context="${pageContext.servletContext.contextPath}" value="/cart" />
    <a href="${contextLink}">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
        PhoneShop
    </a>
    <%--<div >
        <a href="${contextLinkCart}" >Cart : ${cart.totalPrice}</a>
    </div>--%>
</header>

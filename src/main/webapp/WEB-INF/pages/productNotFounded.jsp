<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="id" type="java.lang.Long" scope="request"/>
<tags:master pageTitle="Product List">
    <br/>
    <h1 align="center">Product with id ${id} not found!</h1>
    <h2 align="center">
        <a href="${pageContext.request.contextPath}/products">Back</a>
    </h2>
</tags:master>


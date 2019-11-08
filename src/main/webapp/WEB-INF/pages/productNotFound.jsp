<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="productNotFound" type="com.es.phoneshop.exceptions.ProductNotFoundException" scope="request"/>
<tags:master pageTitle="Product not found">
    <h1 class="text">${productNotFound.message}:C</h1>
</tags:master>

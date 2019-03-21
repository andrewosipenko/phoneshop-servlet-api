<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="code" type="java.lang.String" scope="request"/>
<tags:master pageTitle="Product List">
    <br/>
    <h1 align="center">Product with code '${code}' not found</h1>
</tags:master>


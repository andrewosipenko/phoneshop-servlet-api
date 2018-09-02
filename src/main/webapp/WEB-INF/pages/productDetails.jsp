<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>
<html>
    <head>
        <title>${product.description}</title>
        <style type="text/css">
            <%@include file="/WEB-INF/styles/common.css" %>
        </style>
    </head>
    <body>
        <%@include file="/WEB-INF/common/header.jsp"%>
        <table style="text-align: left">
            <tr>
                <th>Code</th>
                <td>${product.code}</td>
            </tr>
            <tr>
                <th>Descr</th>
                <td>${product.description}</td>
            </tr>
            <tr>
                <th>Price</th>
                <td>${product.price} ${product.currency}</td>
            </tr>
        </table>
        <%@include file="/WEB-INF/common/footer.jsp"%>
    </body>
</html>

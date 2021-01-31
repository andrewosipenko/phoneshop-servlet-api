<jsp:useBean id="errorMsg" scope="request" type="java.lang.String"/>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Web Application Global exception - error page</title>
    </head>
    <body>
        <p>${errorMsg}</p>
    </body>
</html>

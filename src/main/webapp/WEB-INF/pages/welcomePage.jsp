<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<body>
    <h1> Welcome to phoneshop!</h1>
    <form action="${pageContext.servletContext.contextPath}/products" method="get">
        <button>Proceed</button>
    </form>
</body>
</html>
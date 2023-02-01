<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page isErrorPage="true" %>

<tags:master pageTitle="Product not found">
  <h1>
    Product <c:out value = "${requestScope['javax.servlet.error.message']}"/> not found
  </h1>
 <tags:footer/>
</tags:master>
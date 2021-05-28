<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="productIdStr" type="java.lang.String" scope="request"/>
<tags:master pageTitle="Product not found!">
  <p>
    Product with id "${fn:escapeXml(productIdStr)}" is not found!
    Try to start from our <a href="${pageContext.servletContext.contextPath}/products">products page</a>.
  </p>
</tags:master>
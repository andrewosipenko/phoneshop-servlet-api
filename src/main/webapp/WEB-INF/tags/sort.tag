<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>

<c:set var="currentSort" value="${param.sort eq sort and param.order eq order}"/>
<a href="products?sort=${sort}&order=${order}&query=${param.query}"
   style ="${currentSort? 'font-weight:bold': ''}">${order}</a>
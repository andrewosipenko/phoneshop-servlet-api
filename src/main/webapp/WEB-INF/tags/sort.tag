<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="query" required="true" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="<c:url value="/products?query=${param.query}&sort=${sort}&order=asc"/>"/>&#8657</a>
<a href="<c:url value="/products?query=${param.query}&sort=${sort}&order=desc"/>"/>&#8659</a>
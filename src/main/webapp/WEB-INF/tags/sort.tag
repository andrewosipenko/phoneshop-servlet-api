<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="query" required="true" %>
<%@ attribute name="order" required="true" %>
<%@ attribute name="sort" required="true" %>

<a href="<c:url value="/products?query=${query}&sort=${sort}&order=${order}"/>">${order}</a>
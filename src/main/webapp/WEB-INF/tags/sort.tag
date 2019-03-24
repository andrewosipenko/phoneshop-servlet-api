<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortBy" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="?query=${param.query}&order=${order}&sort=${sortBy}">${order}</a>
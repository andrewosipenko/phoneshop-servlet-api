<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortBy" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="?sort=${sortBy}&order=${order}&query=${param.query}">${order}</a>


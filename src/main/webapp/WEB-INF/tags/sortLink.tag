<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>

<%@attribute name="sort" required="true" type="java.lang.String" %>
<%@attribute name="order" required="true" type="java.lang.String" %>
<%@attribute name="query" required="false" type="java.lang.String" %>
<%@attribute name="contentOftags" required="true" type="java.lang.String" %>

<a href = "${pageContext.servletContext.contextPath}/products?sort=${sort}&order=${order}&query=${query}">${contentOftags}</a>

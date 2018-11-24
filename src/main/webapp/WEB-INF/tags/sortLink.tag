<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>

<%@attribute name="sort" required="true" type="java.lang.String" %>
<%@attribute name="order" required="true" type="java.lang.String" %>
<%@attribute name="query" required="false" type="java.lang.String" %>

<a href = "${pageContext.servletContext.contextPath}/products?sort=description&order=asc&query = ${param.query}">asc</a>

<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortField" required="true" %>
<%@ attribute name="sortOrder" required="true" %>

<a href="products?query=${param.query}&sortField=${sortField}&sortOrder=${sortOrder}"
${sortField eq param.sortField and sortOrder eq param.sortOrder ? 'style ="font-weight:bold"' : ''}>${sortOrder}</a>
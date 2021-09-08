<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortField" required="true" %>
<%@ attribute name="sortOrder" required="true" %>

<a href="?sortField=${sortField}&sortOrder=${sortOrder}&searchText=${param.searchText}"
style="${sortField eq param.sortField and sortOrder eq param.sortOrder ? 'color: red' : ''}"
>${sortOrder}</a>

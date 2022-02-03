<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="?sort=${sort}&order=${order}${param.query eq '' or param.query eq null ? '' : '&query='}${param.query}"
style="${sort eq param.sort and order eq param.order ? 'font-weight: bold' : ''}">${order}</a>
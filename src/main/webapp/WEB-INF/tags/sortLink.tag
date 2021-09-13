<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="field" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="?query=${param.query}&field=${field}&order=${order}">
	${ order == "asc"? '&uarr;' : '&darr;' }
</a>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sorting" required="true" %>
<%@ attribute name="type" required="true" %>

<a href="?sorting=${sorting}&type=${type}&description=${param.description}"
   style="${sorting eq param.sorting and type eq param.type ? 'font-weight:bold; text-transform:lowercase;'
   : 'text-transform:lowercase;'}">${type}</a>

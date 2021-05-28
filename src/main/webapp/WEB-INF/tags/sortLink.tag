<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortingCriteria" required="true" %>
<%@ attribute name="sortingOrder" required="true" %>

<a href="?searchQuery=${param.searchQuery}&sortingCriteria=${sortingCriteria}&sortingOrder=${sortingOrder}"
   style = "${sortingOrder eq param.sortingOrder and sortingCriteria eq param.sortingCriteria ? "": "color: grey"}">
    ${sortingOrder eq "asc" ? "&uarr;" : "&darr;"}
</a>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>

<a href = "?sort=${sort}&order=${order}&query=${param.query} ">
<c:choose>
  <c:when test="${sort eq param.sort and order eq param.order}">
       <img src="${order eq 'asc' ? "images/asc-true.png" : "images/desc-true.png"}" width="15" height="15" >
  </c:when>

  <c:otherwise>
    <img src="${order eq 'asc' ? "images/asc-false.png" : "images/desc-false.png"}" width="15" height="15" >
  </c:otherwise>
</c:choose>

</a>

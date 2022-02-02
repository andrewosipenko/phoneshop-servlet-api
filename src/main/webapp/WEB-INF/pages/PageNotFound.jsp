<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="incorrectId" type="java.lang.String" scope="request"/>


<tags:master pageTitle="404">
   <p>
       Page not found with ID ${incorrectId}
   </p>

</tags:master>
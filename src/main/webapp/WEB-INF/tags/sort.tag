<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="search" required="false" %>
<%@attribute name="sortBy" required="true" %>

<c:url value="/products?search=${search}&sortBy=${sortBy}&order=asc" var="ascSorting"/>
<a href="${ascSorting}">&uarr;</a>
<c:url value="/products?search=${search}&sortBy=${sortBy}&order=desc" var="descSorting"/>
<a href="${descSorting}">&darr;</a>

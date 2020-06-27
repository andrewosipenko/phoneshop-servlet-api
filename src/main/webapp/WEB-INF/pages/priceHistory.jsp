<%--
  Created by IntelliJ IDEA.
  User: Арсений Камадей
  Date: 25.06.2020
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>
<tags:master pageTitle="Price History">
    <h2>${product.description}</h2>
    <table>
        <thead>
        <td>Date</td>
        <td>Cost</td>
        </thead>
        <c:forEach var="price" items="${product.prices}">
            <tr>
                <td>${price.date}</td>
                <td>${price.cost}</td>
            </tr>
        </c:forEach>
    </table>
</tags:master>
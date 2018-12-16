<%--
  Created by IntelliJ IDEA.
  User: Anna
  Date: 16.12.2018
  Time: 5:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Order">
    <jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>

    Name: ${order.name}
    <br><br>
    Delivery address: ${order.deliveryAddress}
    <br><br>
    Phone: ${order.phone}
    <br><br><br>

</tags:master>
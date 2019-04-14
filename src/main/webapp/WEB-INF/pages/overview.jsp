<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.product.dao.order.Order" scope="request"/>
<tags:master pageTitle="Overview">
    <p>
    <h2>Thanks for purchase</h2>
    <p>

        <form method="post">
    <p>
    <div>
        First name: ${order.firstName}
    </div>
    <p>
    <div>
        Last name: ${order.lastName}
    </div>
    <p>
    <div>
        Phone: ${order.phone}
    </div>
    <p>
    <div>
        Delivery mode: ${order.deliveryMode.name}
    </div>
    <p>
    <div>
        Delivery date: ${order.deliveryDate}
    </div>
    <p>
    <div>
        Delivery costs: $${order.deliveryMode.price}
    </div>
    <p>
    <div>
        Delivery address: ${order.deliveryAddress}
    </div>
    <p>
    <div>
        Payment method: ${order.paymentMethod.name}
    </div>

</tags:master>
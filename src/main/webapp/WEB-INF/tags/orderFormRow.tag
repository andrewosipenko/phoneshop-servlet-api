<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="order" required="true" type="com.es.phoneshop.model.order.entity.Order" %>
<%@ attribute name="errors" required="true" type="java.util.Map" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr>
    <td>${label}<span style="color: red">*</span></td>
    <td>
        <c:set var="error" value="${errors[name]}"/>
        <input name=${name} value="${not empty error ? param[name] : order[name]}">
    </td>
</tr>
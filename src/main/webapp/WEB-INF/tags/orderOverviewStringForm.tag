<%@ tag trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ attribute name="order" required="true" type="com.es.phoneshop.model.product.entity.Order" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>

<td>${label}</td>
<td>
    ${order.customerInfo[name]}
</td>
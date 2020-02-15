<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<p>
    Total quantity: ${miniCart.totalQuantity}
</p>

<p>Total price:
    <fmt:formatNumber value="${miniCart.totalPrice}" type="currency" currencySymbol="USD"/>
</p>

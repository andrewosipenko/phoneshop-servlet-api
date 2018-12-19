<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <a href="<c:url value="/products"/>">
        <img src="<c:url value="/images/logo.svg"/>">
        PhoneShop   Total ammount:${cart.totalPrice}
    </a>
</header>
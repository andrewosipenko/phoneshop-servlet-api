
<header>
    <a href="${pageContext.servletContext.contextPath}">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
        PhoneShop
    </a>
    <div class="cart-in-header">
        <a href="${pageContext.servletContext.contextPath}/cart">Cart: </a>
        ${cart.totalPrice}
    </div>
</header>

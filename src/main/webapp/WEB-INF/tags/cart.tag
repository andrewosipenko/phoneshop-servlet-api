<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>

<c:if test="${not empty requestScope['cart'].cartItems}">
    <br>
    <h2>Your cart</h2>
    <br>
    <table>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td>Price</td>
            <td>Quantity</td>
        </tr>

        <c:forEach items="${requestScope['cart'].cartItems}" var="cartItem">
            <tr>
                <td>
                    <img class="product-tile" src="${cartItem.product.imageUrl}" alt="cart.product.code">
                </td>
                <td>${cartItem.product.description}</td>
                <td>${cartItem.product.price}</td>
                <td>${cartItem.quantity}</td>
            </tr>
        </c:forEach>

    </table>
</c:if>
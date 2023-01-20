<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="cart" type="com.es.phoneshop.model.cart.Cart" required="true" %>

<c:if test="${cart.items.size() > 0}">
    <div>
        <h3>
            Cart
        </h3>
        <table>
            <thead>
            <tr>
                <td>
                    Image
                </td>
                <td>
                    Description
                </td>
                <td>
                    Quantity
                </td>
            </tr>
            </thead>
            <c:forEach var="cartItem" items="${cart.items}">
                <tr>
                    <td>
                        <img class="product-tile" src="${cartItem.product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${cartItem.product.id}">
                                ${cartItem.product.description}
                        </a>
                    </td>
                    <td>
                            ${cartItem.quantity}
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>

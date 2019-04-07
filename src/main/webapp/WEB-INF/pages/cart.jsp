<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="httpCart" type="com.es.phoneshop.model.product.cart.Cart" scope="session"/>
<tags:master pageTitle="Cart">
    <span style="color: green;">
        <h4>${param.message}</h4>
    </span>
    <c:if test="${not empty requestScope['errors']}">
        <span style="color: red">
            <h4>Error: please check the quantities of products</h4>
        </span>
    </c:if>
    <p>
        <form action="${pageContext.servletContext.contextPath}/cart" method="post">
            <table>
                <tr>
                    <td>Image</td>
                    <td>Description</td>
                    <td>Price</td>
                    <td>Quantity</td>
                    <td>Action</td>
                </tr>
                <c:forEach items="${httpCart.cartItems}" var="cartItem" varStatus="loop">
                    <c:set var="product" value="${cartItem.product}"/>
                    <tr>
                        <td>
                            <img class="product-tile" src="${product.imageUrl}" alt="${product.code}">
                        </td>
                        <td>
                            <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                                    ${product.description}
                            </a>
                        </td>
                        <td>$${product.price}</td>
                        <td>
                            <input type="hidden" name="id" value="${product.id}"/>
                            <input type="text" name="quantity"
                                   value="${not empty paramValues.quantity[loop.index]
                                    ? paramValues.quantity[loop.index]
                                    : cartItem.quantity}">
                            <c:if test="${not empty requestScope['errors']}">
                                <br>
                                <span style="color: red">
                                        ${requestScope['errors'][loop.index]}
                                </span>
                            </c:if>
                        </td>
                        <td>
                            <button formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${product.id}">
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td>Total</td>
                    <td colspan="2" align="right">$${httpCart.totalPrice}</td>
                </tr>
            </table>
    <p>
        <button>Update</button>
    </p>
    </form>
    </p>
</tags:master>

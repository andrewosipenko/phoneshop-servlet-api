<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.entity.Cart" scope="request"/>

<tags:master pageTitle="Product Details">


    <c:if test="${not empty param.message}">
        <div class="success">
                ${param.message}
        </div>
    </c:if>

    <c:if test="${not empty param.error}">
        <div class="error">
                ${param.error}
        </div>
    </c:if>


    <div>
        <h1>
            Cart
        </h1>
    </div>
    <a href=${pageContext.servletContext.contextPath}/products>
        <p>
            return to product list
        </p>
    </a>

    <%--todo move into tag--%>
    <form method="post" action="">
        <div>
            <table>
                <thead>
                <tr>
                    <td>Image</td>
                    <td class="description">
                        Description
                    </td>
                    <td class="price">
                        Price
                    </td>
                    <td>
                        Quantity
                    </td>
                </tr>
                </thead>
                <c:forEach var="cartItem" items="${cart.items}">
                    <tr>
                        <td>
                            <img class="product-tile" src=${cartItem.product.imageUrl}>
                        </td>
                        <td>
                                ${cartItem.product.description}
                        </td>
                        <td>
                                ${cartItem.product.price}
                        </td>
                        <td>
                            <fmt:formatNumber value="${cartItem.quantity}" var="quantity"/>
                            <input class="quantity" type="text" name="quantity" value="${cartItem.quantity}">
                            <input type="hidden" name="productId" value="${cartItem.product.id}"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <p>
            <button>
                Update
            </button>
        </p>
    </form>
    <tags:recentlyViewed recentlyViewed="${recentlyViewed}"/>
</tags:master>

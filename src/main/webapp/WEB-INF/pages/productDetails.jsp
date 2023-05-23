<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>
<jsp:useBean id="products" type="java.util.LinkedList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Cart: ${cart}
    </p>
    <p>
        Product "${product.description}"
    </p>
    <c:if test="${not empty param.message && empty error}">
        <p class="success">
                ${param.message}
        </p>
    </c:if>
    <c:if test="${not empty error}">
        <p class="error">
            An error occurred while adding to the cart
        </p>
    </c:if>
    <form method="post">
        <table>
            <tr>
                <td>Image</td>
                <td><img src="${product.imageUrl}"></td>
            </tr>
            <tr>
                <td>Code</td>
                <td>${product.code}</td>
            </tr>
            <tr>
                <td>Stock</td>
                <td>${product.stock}</td>
            </tr>
            <tr>
                <td>Price</td>
                <td>
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
            <tr>
                <td>Quantity</td>
                <td>
                    <input name="quantity" value="${not empty error ? param.quantity : 1}">
                    <c:if test="${not empty error}">
                        <p class="error">
                                ${error}
                        </p>
                    </c:if>
                </td>
            </tr>
        </table>
        <p>
            <button>Add to cart</button>
        </p>
    </form>
    <footer>
        <h1>Recently Viewed</h1>
        <div id="mainDiv">
            <c:forEach var="product" items="${products}">
                <table>
                    <tr>
                        <td><img src="${product.imageUrl}"></td>
                    </tr>
                    <tr>
                        <td>
                            <a href="${pageContext.servletContext.contextPath}/products/${product.productId}">
                                    ${product.description}
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </td>
                    </tr>
                </table>
            </c:forEach>
        </div>
    </footer>
</tags:master>

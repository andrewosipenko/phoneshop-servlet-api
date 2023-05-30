<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="productsRecentlyViewed" type="java.util.LinkedList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form>
        <input type="text" name="description" value="${param.description}">
        <button>Search</button>
    </form>
    <c:if test="${not empty param.message && empty errors}">
        <p class="success">
                ${param.message}
        </p>
    </c:if>
    <c:if test="${not empty errors}">
        <p class="error">
            An error occurred while adding to the cart
        </p>
    </c:if>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
                <tags:sortLink sorting="DESCRIPTION" type="ASC"></tags:sortLink>
                <tags:sortLink sorting="DESCRIPTION" type="DESC"></tags:sortLink>
            </td>
            <td class="quantity">Quantity</td>
            <td class="price">
                Price
                <tags:sortLink sorting="PRICE" type="ASC"></tags:sortLink>
                <tags:sortLink sorting="PRICE" type="DESC"></tags:sortLink>
            </td>
            <td>Operation</td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}" varStatus="status">
            <form action="${pageContext.servletContext.contextPath}/products/addCartItem/${product.id}"
                  method="post">
                <tr>
                    <td>
                        <img class="product-tile" src="${product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                                ${product.description}
                        </a>
                    </td>
                    <td class="quantity">
                        <c:set var="err" value="${errors[product.id]}"/>
                        <input name="quantity"
                               value="${not empty err ? param.quantity : 1}"
                               class="quantity"/>
                        <c:if test="${not empty err}">
                            <p class="error">
                                    ${errors[product.id]}
                            </p>
                        </c:if>
                        <input name="id" type="hidden" value="${product.id}"/>
                    </td>
                    <td class="price">
                        <a href="${pageContext.servletContext.contextPath}/history/${product.id}">
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </a>
                    </td>
                    <td>
                        <button>Add to cart</button>
                    </td>
                </tr>
            </form>
        </c:forEach>
    </table>
    <footer>
        <h1>Recently Viewed</h1>
        <div id="mainDiv">
            <c:forEach var="product" items="${productsRecentlyViewed}">
                <table>
                    <tr>
                        <td><img src="${product.imageUrl}"></td>
                    </tr>
                    <tr>
                        <td>
                            <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
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

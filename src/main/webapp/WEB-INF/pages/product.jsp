<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.entity.product.Product" scope="request"/>
<tags:master pageTitle="Product Page">
    <p>
        ${product.description}
    </p>

    <table>
        <tr>
            <td>Image</td>
            <td>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>

        </tr>

        <tr>
            <td>Code</td>
            <td>
                ${product.code}
            </td>
        </tr>

        <tr>
            <td>Price</td>
            <td>
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
        </tr>

        <tr>
            <td>Stock</td>
            <td>
                ${product.stock}
            </td>
        </tr>

        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                            ${product.description}
                    </a>
                </td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>
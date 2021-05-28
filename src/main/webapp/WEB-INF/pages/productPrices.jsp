<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="prices" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="product" type="com.es.phoneshop.domain.product.model.Product" scope="request"/>

<tags:master pageTitle="Product prices history">
    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
    <table>
        <thead>
        <tr>
            <td>From</td>
            <td class="price">
                Price
            </td>
        </tr>
        </thead>
        <c:forEach var="price" items="${prices}">
            <tr>
                <td>
                    <fmt:parseDate value="${price.from}" pattern="yyyy-MM-dd" var="priceFrom" type="date"/>
                    <fmt:formatDate pattern="dd MMM yyyy" value="${priceFrom}"/>
                </td>
                <td>
                    <fmt:formatNumber value="${price.value}" type="currency" currencySymbol="${price.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request" />
<tags:master pageTitle="Product Price History">
    <h1>
        ${product.description}
    </h1>
    <table>
        <thead>
            <tr>
                <td>Start date</td>
                <td>Price</td>
            </tr>
        </thead>
        <c:forEach var="history" items="${product.historyList}">
            <tr>
                <td>
                    ${history.statDate}
                </td>
                <td class="price">
                    <fmt:formatNumber value="${history.price}" type="currency" currencySymbol="${history.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>
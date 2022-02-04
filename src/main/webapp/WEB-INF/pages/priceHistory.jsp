<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.bean.Product" scope="request"/>
<tags:master pageTitle="Price History">
    <p>
            ${product.description}
    </p>
    <table>
        <thead>
        <td> Start date</td>
        <td> Price</td>
        </thead>
        <c:forEach var="priceHistory" items="${product.priceHistoryLog}">
            <tr>
                <td>${priceHistory.dateStart}</td>
                <td class="price">
                    <fmt:formatNumber value="${priceHistory.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>

</tags:master>


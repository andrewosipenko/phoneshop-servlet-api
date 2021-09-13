<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product details">
    <p>
        Product details page!
    </p>
    <table>
        <tr>
            <td>${product.description}</td>
            <td><img src="${product.imageUrl}" alt="Product image"></td>
        </tr>
        <tr>
            <td>
                <a onclick="myFunction(${product.id})">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </a>
                <script>
                    function myFunction(id) {
                        window.open("${pageContext.servletContext.contextPath}/priceHistory/" + id,
                            "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
                    }
                </script>
            </td>
            <td>stock - ${product.stock}</td>
        </tr>
    </table>
</tags:master>
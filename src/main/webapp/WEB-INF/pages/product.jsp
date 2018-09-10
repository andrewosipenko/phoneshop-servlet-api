<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>
        <html>
            <head></head>
            <body>
                <jsp:include page="header.jsp"/>
                <p>
                PDP
                </p>
                <form method=post>
                <table>
                    <tr>
                    <td>ID</td>
                    <td>${product.id}</td>
                    </tr>
                    <tr>
                    <td>Code</td>
                    <td>${product.code}</td>
                    </tr>
                    <tr>
                    <td>Description</td>
                    <td>${product.description}</td>
                    </tr>
                    <tr>
                    <td>Price</td>
                    <td>${product.price}</td>
                    </tr>
                    <tr>
                    <td>Currency</td>
                    <td>${product.currency}</td>
                    </tr>
                    </tr>
                    <tr>
                    <td>Stock</td>
                    <td>${product.stock}</td>
                    </tr>
                    <tr>
    <td>
    <input type = "text" name = "quantity" value = "1" style = "text-align: right">
    <c:if test = "${error}">
    <p style="color: red">${error}</p>
    </c:if>
    </td>
    <td>
        <input type = "submit" value = "add to Cart">
    </td>
    </tr>
                </table>
                </form>
                <jsp:include page="footer.jsp"/> 
            </body>
        </html>

<header>
    <jsp:include page="/WEB-INF/pages/header.jsp"/>
</header>

<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>

<p>
    Hello from product details page!
</p>

<form method=post>
    <table >
        <thead>
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
                <input type = "text" name = "quantity" id = quantity value = "1" style = "text-align: right">
                <label for = quantity style="color: red">${error}</label>
            </td>
            <td>
                <input type = "submit" value = "add to Cart">
            </td>
        </tr>
        </thead>
    </table>
</form>
<footer>
    <jsp:include page="/WEB-INF/pages/footer.jsp"/>
</footer>
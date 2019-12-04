<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Detail">
    <h3>
        ${product.description}
    </h3>

    <p>
        <c:choose>
            <c:when test="${not empty error}">
                <span class="message-red">Error adding to cart!</span>
            </c:when>
            <c:when test="${param.success}">
                <span class="message-green">Added to cart successfully!</span>
            </c:when>
        </c:choose>
    </p>
    <table class="table table-bordered" style="width:50%;">
        <thead>
        <tr>
            <th scope="col">Image</th>
            <th scope="col">Description</th>
            <th scope="col">Price</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row">
                <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </th>
            <td>${product.description}</td>
            <td><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></td>
        </tr>
        </tbody>
    </table>
    <form method="post" action="${pageContext.servletContext.contextPath}/products/${product.id}">
        <div class="form-row">
            <div class="col-1" style="margin-left:10px;">
                <p>Quantity:</p>
            </div>
            <div class="col-3">
                <input type="text" class="form-control" name="quantity" value="${not empty param.quantity ? param.quantity : 1}">
            </div>
            <div class="col-3">
                <button class="btn btn-dark">Add to cart</button>
            </div>
        </div>
        <p>
            <c:if test="${not empty error}">
                <span class="message-red">${error}</span>
            </c:if>
        </p>
    </form>
    <c:if test="${not empty product.reviewArrayList}">
        <h4>Comments</h4>
        <table>
        <c:forEach var="review" items="${product.reviewArrayList}">
            <tr>
                <td>
                    <p>Name: ${review.name}</p>
                    <p>Rating: ${review.rating}</p>
                    <p>Comment: ${review.comment}</p>
                </td>
            </tr>
        </c:forEach>
        </table>
    </c:if>
    <form action="${pageContext.servletContext.contextPath}/productReview/${product.id}" method="post">
        <h4>Add your comment</h4>
        <table>
            <tr>
                <tags:field label="Name" name="name" errorMap="${errorMap1}"/>
            </tr>
            <tr>
                <tags:ratingField label="Rating" name="rating" errorMap="${errorMap1}"/>
            </tr>
            <tr>
                <tags:field label="Comment" name="comment" errorMap="${errorMap1}"/>
            </tr>
        </table>
        <br>
        <button class="btn btn-dark button-margin">Add comment</button>
        <br>
    </form>
    <c:if test="${not empty viewedProducts}">
        <h3>Viewed Products</h3>
    </c:if>
    <div class="col-9">
        <div class="row">
            <c:forEach var="product" items="${viewedProducts.viewedProducts}">
                <div class="card col-3">
                    <img
                            src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}"
                            class="rounded mx-auto d-block"
                            alt="..."
                    />
                    <div class="card-body">
                        <h5 class="card-title"><a href="products/${product.id}">${product.description}</a></h5>
                        <p class="btn btn-primary">
                            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                        </p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</tags:master>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<head>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
   <c:if test = "${not empty param.message and empty errors}">
        <div class = "success">
            ${param.message}
        </div>
      </c:if>

    <c:if test = "${not empty errors}">
      <div class = "error">
          Error while adding to cart
      </div>
    </c:if>
  <form>
    <input name = "query" value = "${param.query}">
    <button>Search</button>
  </form>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>Description
            <tags:sort sort = "description" order = "asc"/>
            <tags:sort sort = "description" order = "desc"/>
        </td>
        <td class="price">Price
            <tags:sort sort = "price" order = "asc"/>
            <tags:sort sort = "price" order = "desc"/>
        </td>
        <td>Quantity</td>
        <td></td>

      </tr>
    </thead>
    <c:forEach var="product" items="${products}">

      <tr>
        <td>
          <img class="product-tile" src="${product.imageUrl}">
        </td>
        <td>
           <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
              ${product.description}
           </a>
        </td>
        <td class="price">
           <a href="#see/${product.id}">

            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
          </a>

        </td>
        <form method="post">
        <td>
         <input name = "quantity" value = "${not empty errors[product.id] ? param.quantity : 1}">
         <input name="productId" type="hidden" value=${product.id}>
                     <c:if test = "${not empty errors[product.id]}">
                                 <div class = "error">
                                     ${errors[product.id]}
                                 </div>
                      </c:if>
        </td>
        <td>

            <button>Add to cart</button>


        </td>
        </form>
      </tr>
      <a href="#" id="see/${product.id}">
                              <div id="popup">
                                <h2>Price history</h2>
                                <p>${product.description}</p>
                                <c:forEach var="price" items="${product.priceHistory}">
                                   From <fmt:formatDate value="${price.key}" pattern="yyyy-MM-dd" /> price <fmt:formatNumber value="${price.value}" type="currency" currencySymbol="${product.currency.symbol}"/>
                                </c:forEach>
                              </div>
                     </a>
    </c:forEach>
  </table>


   <tags:footer />

</tags:master>
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
      </tr>
      <a href="#" id="see/${product.id}">
                              <div id="popup">
                                <h2>Price history</h2>
                                <p>${product.description}</p>
                                <c:forEach var="price" items="${product.priceHistory}">
                                   C ${price.key} цена <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                                </c:forEach>
                              </div>
                     </a>
    </c:forEach>
  </table>

  <style>
      [id*="see"] {
      display: none;
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
    }
    #popup {
      width: 250px;
      height: 300px;
      text-align: center;
      border: 1px solid gray;
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
      margin: auto;
    }
    [id*="see"]:target {display: block;}
  </style>

   <tags:footer />
</tags:master>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details">
  <table>
    <thead>
      <tr>
        <td>Description</td>
        <td>
          ${product.description}
        </td>
      </tr>
      <tr>
        <td>Stock</td>
        <td>
            ${product.stock}
        </td>
      </tr>
      <tr>
        <td>Price</td>
        <td>
            ${product.price}
        </td>
      </tr>
      <tr>
        <td>Image</td>
        <td>
            <img src="${product.imageUrl}"/>
        </td>
      </tr>
    </thead>
  </table>
</tags:master>
package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.ProductService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.es.phoneshop.sort.SortField.price;
import static com.es.phoneshop.sort.SortOrder.asc;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductListPageServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletConfig config;
    @Mock
    private ServletContext context;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private ProductService productService;

    private ProductListPageServlet servlet;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        servlet = new ProductListPageServlet();
        servlet.init(config);
        when(config.getServletContext()).thenReturn(context);
        when(context.getRequestDispatcher("/WEB-INF/pages/productList.jsp")).thenReturn(dispatcher);
    }

    @Test
    public void givenValidProductList_whenDoGet_thenValidAttributeAndForward() throws Exception {
        String query = "Samsung";
        String sortField = "price";
        String sortOrder = "asc";

        List<Product> products = new ArrayList<>();
        products.add(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), null, 100, null));
        products.add(new Product("sgs", "Samsung Galaxy S II", new BigDecimal(100), null, 100, null));
        products.add(new Product("sgs", "Samsung Galaxy S III", new BigDecimal(100), null, 100, null));

        when(request.getParameter("query")).thenReturn(query);
        when(request.getParameter("sort")).thenReturn(sortField);
        when(request.getParameter("order")).thenReturn(sortOrder);
        when(productService.findProducts(eq(query), eq(String.valueOf(price)), eq(String.valueOf(asc)))).thenReturn(products);
        when(request.getRequestDispatcher("/WEB-INF/pages/productList.jsp")).thenReturn(dispatcher);
        servlet.setProductService(productService);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("products"), eq(products));
        verify(dispatcher).forward(request, response);
    }
}

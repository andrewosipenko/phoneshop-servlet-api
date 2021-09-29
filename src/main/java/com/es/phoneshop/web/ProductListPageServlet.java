package com.es.phoneshop.web;

import com.es.phoneshop.model.product.productdao.ArrayListProductDao;
import com.es.phoneshop.model.product.productdao.Product;
import com.es.phoneshop.model.product.productdao.ProductDao;
import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.DefaultCartService;
import com.es.phoneshop.model.product.enums.sort.SortField;
import com.es.phoneshop.model.product.enums.sort.SortOrder;
import com.es.phoneshop.model.product.exceptions.QuantityLowerZeroException;
import com.es.phoneshop.model.product.exceptions.StockException;
import com.es.phoneshop.model.product.recentlyview.DefaultRecentlyViewService;
import com.es.phoneshop.model.product.recentlyview.RecentlyViewSection;
import com.es.phoneshop.model.product.recentlyview.RecentlyViewService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListPageServlet extends HttpServlet {

    private static final String SEARCH_TEXT = "searchText";
    private static final String SORT_ORDER = "sortOrder";
    private static final String SORT_FIELD = "sortField";
    public static final String RECENTLY_VIEW_SECTION = "recentlyViewSection";
    public static final String PRODUCT_LIST_PAGE_JSP = "/WEB-INF/pages/productList.jsp";
    public static final String PRODUCTS_QUANTITIES_MAP = "productsQuantitiesMap";

    private ProductDao productDao;
    private RecentlyViewService recentlyViewService;
    private CartService cartService;
    private WebHelperService webHelperService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = DefaultCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
        recentlyViewService = DefaultRecentlyViewService.getInstance();
        webHelperService = WebHelperService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<Product, Integer> productsQuantities = new HashMap<>();
        List<Product> productList = productDao.findProducts(null, null, null);
        Cart cart = cartService.getCart(request);
        for (Product productItem : productList) {
            int quantityOfProduct = cartService.getQuantityOfCartItem(cart, productItem);
            productsQuantities.put(productItem, quantityOfProduct);
        }
        request.setAttribute(PRODUCTS_QUANTITIES_MAP, productsQuantities);
        String searchText = request.getParameter(SEARCH_TEXT);
        List<String> searchTextList = searchText != null ? parseSearchText(request) : null;
        String sortOrder = request.getParameter(SORT_ORDER);
        String sortField = request.getParameter(SORT_FIELD);
        RecentlyViewSection recentlyViewSection = recentlyViewService.getRecentlyViewSection(request);
        request.setAttribute(RECENTLY_VIEW_SECTION, recentlyViewSection);
        request.setAttribute("products", productDao.findProducts(searchTextList,
                sortField != null ? SortField.valueOf(sortField) : null,
                sortOrder != null ? SortOrder.valueOf(sortOrder) : null));
        request.getRequestDispatcher(PRODUCT_LIST_PAGE_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdString = request.getParameter("productId");
        String quantityString = request.getParameter("quantity");
        Long productId = Long.parseLong(productIdString);
        int quantity;
        try {
            quantity = webHelperService.parseRightQuantity(request, quantityString);
            cartService.putToCart(cartService.getCart(request), productId, quantity);
        } catch (NumberFormatException | ParseException exception) {
            setErrorMessage(request, response, "Quantity should be integer", productId);
            return;
        } catch (StockException exception) {
            setErrorMessage(request, response,
                    "Not enough stock, available " + productDao.getProduct(productId).getStock(),
                    productId);
            return;
        } catch (QuantityLowerZeroException exception) {
            setErrorMessage(request, response, "Quantity should be > 0", productId);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/products?successMessage=Cart successfully updated");
    }

    private void setErrorMessage(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String errorMessage,
                                 Long id) throws ServletException, IOException {
        request.setAttribute("productIdWithError", id);
        request.setAttribute("error", errorMessage);
        doGet(request, response);
    }

    private List<String> parseSearchText(HttpServletRequest request) {
        return Arrays.asList(request.getParameter(SEARCH_TEXT).split("\\s"));
    }
}

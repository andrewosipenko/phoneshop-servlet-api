package com.es.phoneshop.model.cart;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultCartServiceTest {
    private CartService cartService;
    private ProductDao productDao;

    @Before
    public void init() {
        cartService = DefaultCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    @SneakyThrows
    @Test
    public void addProductToCartSuccess() {
        final Currency usd = Currency.getInstance("USD");
        final Product product = new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        product.setId(1L);
        productDao.save(product);
        final Cart cart = new Cart();
        cartService.add(cart, 1L, 10);

        assertTrue(cart.getItems().stream()
                .anyMatch(item -> item.getProduct().getId().equals(product.getId())));
    }

    @Test
    public void addProductToCartNullCart() {
        assertThrows(NullPointerException.class,
                () -> cartService.add(null, 1L, 10));
    }

    @Test
    public void addProductToCartIncorrectProductId() {
        assertThrows(IllegalArgumentException.class,
                () -> cartService.add(new Cart(), (long) -15, 10));
    }

    @SneakyThrows
    @Test
    public void addProductToCartIncorrectQuantity() {
        assertThrows(IllegalArgumentException.class,
                () -> cartService.add(new Cart(), 1L, 0));
    }

    @Test
    public void addProductToCartProductNotFound() {
        final Long id = 1L;
        final Cart cart = new Cart();
        assertFalse(cart.getItems().stream()
                .anyMatch(item -> item.getProduct().getId().equals(id)));
        assertThrows(ProductNotFoundException.class,
                () -> cartService.add(cart, id, 10));
    }

    @Test
    public void addProductToCartOutOfStock() {
        final Currency usd = Currency.getInstance("USD");
        final Long id = 1L;
        final int stock = 40;
        final Cart cart = new Cart();
        Product product = new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, stock, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        product.setId(id);
        productDao.save(product);

        assertThrows(OutOfStockException.class,
                () -> cartService.add(cart, id, stock + 10));
    }

    @SneakyThrows
    @Test
    public void addProductToCartAgainOutOfStock() {
        final Currency usd = Currency.getInstance("USD");
        final Long id = 1L;
        final int stock = 40;
        final Cart cart = new Cart();
        Product product = new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, stock, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        product.setId(id);
        productDao.save(product);
        cartService.add(cart, id, stock);

        assertThrows(OutOfStockException.class,
                () -> cartService.add(cart, id, 1));
    }


}
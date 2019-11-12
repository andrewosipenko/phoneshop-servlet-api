package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.IllegalQuantityException;
import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

public class HttpSessionCartService implements CartService{
    private static volatile CartService INSTANCE;
    private static final String CART_ATTRIBUTE = "cart";
    private static final String SUCCESS_PARSING = "success parsing";
    private ProductDao productDao = ArrayListProductDao.getInstance();

    private HttpSessionCartService() {
    }

    public static CartService getInstance() {
        if(INSTANCE == null){
            synchronized (HttpSessionCartService.class){
                if(INSTANCE == null){
                    INSTANCE = new HttpSessionCartService();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public String add(HttpSession session, Cart cart, Long productId, String quantity, Locale locale)
    throws LackOfStockException, IllegalQuantityException, NumberFormatException {
        Product product = productDao.getProduct(productId);
        String resultOfParsingQuantity = parseQuantity(locale, quantity, product.getStock());
        if (resultOfParsingQuantity.equals(SUCCESS_PARSING)) {
            int quantityInt = Integer.parseInt(quantity);
            Optional<CartItem> optionalCartItem = cart
                    .getCartItems()
                    .stream()
                    .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                    .findFirst();
            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantityInt);
            } else {
                cart.getCartItems().add(new CartItem(product, quantityInt));
            }
            calculateTotalQuantity(cart);
            calculateTotalPrice(cart);
            return null;
        } else {
            return resultOfParsingQuantity;
        }
    }

    private String parseQuantity(Locale locale, String quantity, int stock) throws LackOfStockException,
            IllegalQuantityException, NumberFormatException{
        try {
            int quantityInt = Integer.parseInt(String.valueOf(NumberFormat.getInstance(locale).parse(quantity)));
            if (quantityInt < 0) {
                throw new IllegalQuantityException("Quantity should be greater 0");
            }
            if (quantityInt > stock) {
                throw new LackOfStockException("Error of stock! Max stock = " + stock);
            }
        } catch (ParseException exception) {
            throw new NumberFormatException("Not a number!");
        }
        return SUCCESS_PARSING;
    }

    @Override
    public void calculateTotalPrice(Cart cart) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
    }

    @Override
    public void calculateTotalQuantity(Cart cart) {
        int totalQuantity = cart
                .getCartItems()
                .stream()
                .map(CartItem::getQuantity)
                .reduce(Integer::sum)
                .orElse(0);
        cart.setTotalQuantity(totalQuantity);
    }

    @Override
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_ATTRIBUTE, cart);
        }
        return cart;
    }
}

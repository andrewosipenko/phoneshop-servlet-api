package com.es.phoneshop.cart;

import com.es.phoneshop.exceptions.IncorrectInputException;
import com.es.phoneshop.lock.SessionLock;
import com.es.phoneshop.lock.SessionLockService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Optional;

public class DefaultCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";
    private static CartService instance;
    private static final Object mutex = new Object();
    private final ProductDao productDao;
    private final SessionLock lock;

    public static CartService getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new DefaultCartService();
                }
            }
        }
        return instance;
    }

    private DefaultCartService() {
        productDao = ArrayListProductDao.getInstance();
        lock = SessionLockService.getInstance();
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        lock.getSessionLock(request).lock();
        try {
            Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
            if (cart == null) {
                request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
            }
            return cart;
        } finally {
            lock.getSessionLock(request).unlock();
        }
    }

    private void inputCheck(HttpServletRequest request, String productId, String quantity) throws IncorrectInputException {
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        int quantityInt;
        try {
            quantityInt = format.parse(quantity).intValue();
            if (quantityInt < 0) {
                throw new IncorrectInputException("Negative amount");
            }

            Optional<Product> product = productDao.getProduct(Long.valueOf(productId));
            if (!product.isPresent()) {
                throw new IncorrectInputException("Product not found");
            }

            if (quantityInt == 0) {
                throw new IncorrectInputException("Product not added to cart, because amount is 0");
            }

            if (product.get().getStock() < quantityInt) {
                throw new IncorrectInputException("Out of stock");
            }

        } catch (ParseException e) {
            throw new IncorrectInputException("Not a number");
        }
    }

    @Override
    public void update(HttpServletRequest request, String productId, String quantity) throws IncorrectInputException {
        lock.getSessionLock(request).lock();
        try {
            //  if (inputCheck(request,productId,quantity)) {
            inputCheck(request, productId, quantity);
            getCart(request).getCartItemByProductId(Long.valueOf(productId))
                    .ifPresent(cartItem -> cartItem.setQuantity(Integer.parseInt(quantity)));
//            }else{
//                throw new IncorrectInputException();
//            }
        } finally {
            lock.getSessionLock(request).unlock();
        }
    }

    @Override
    public void add(HttpServletRequest request, String productId, String quantity) throws IncorrectInputException {
        lock.getSessionLock(request).lock();
        try {
            inputCheck(request, productId, quantity);
            if (isEnoughStockForOrder(request, productId, quantity)) {
                CartItem cartItem = new CartItem(productDao.getProduct(Long.valueOf(productId))
                        .get(), Integer.parseInt(quantity));
                if (getCart(request).getItems().isEmpty()) {
                    getCart(request).getItems().add(cartItem);
                    return;
                }
                for (CartItem item : getCart(request).getItems()) {
                    if (item.equals(cartItem)) {
                        item.setQuantity(item.getQuantity() + Integer.parseInt(quantity));
                        return;
                    }
                }
                getCart(request).getItems().add(cartItem);
            } else {
                throw new IncorrectInputException("Out of stock");
            }
        } finally {
            lock.getSessionLock(request).unlock();
        }
    }

    private boolean isEnoughStockForOrder(HttpServletRequest request, String productId, String quantity) {
        lock.getSessionLock(request).lock();
        try {
            if (!productDao.getProduct(Long.valueOf(productId)).isPresent()) {
                return false;
            }
            int currentAmount = getCart(request).getCurrentQuantityById(Long.valueOf(productId));
            return Integer.parseInt(quantity) + currentAmount <=
                    productDao.getProduct(Long.valueOf(productId)).
                            get().getStock();
        } finally {
            lock.getSessionLock(request).unlock();
        }
    }
}

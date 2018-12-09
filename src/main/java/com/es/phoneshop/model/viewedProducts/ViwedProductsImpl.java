package com.es.phoneshop.model.viewedProducts;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ViwedProductsImpl implements ViewedProductsService {
    private List<Product> viewList;

    private static final String VIEWED_ATTRIBUTE = "viewed";
    private static volatile ViewedProductsService viewedProductsService;

    private ViwedProductsImpl() {
        viewList = new ArrayList<>();
    }

    public static ViewedProductsService getInstance() {
        if (viewedProductsService == null) {
            synchronized (ViwedProductsImpl.class) {
                if (viewedProductsService == null) {
                    viewedProductsService = new ViwedProductsImpl();
                }
            }
        }
        return viewedProductsService;
    }

    @Override
    public List<Product> getViewedProducts(HttpSession session) {
        viewList = (List<Product>) session.getAttribute(VIEWED_ATTRIBUTE);
        if (viewList == null) {
            viewList = new ArrayList<>();
            session.setAttribute(VIEWED_ATTRIBUTE, viewList);
        }
        return viewList;
    }

    @Override
    public void addToViewedProducts (List<Product> list, Product product) {
        if (list.stream()
                .anyMatch(product1 -> product1.getId()
                        .equals(product.getId()))) {
            list.remove(product);
        } else {
            if (list.size() == 3) {
                list.remove(0);
            }
        }
        list.add(product);
    }
}

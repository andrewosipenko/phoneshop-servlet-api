package com.es.phoneshop.model.lastViewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

public class LastViewed {
    private List<Product> lastViewed;

    public LastViewed(HttpSession session) {
        lastViewed = (List<Product>) session.getAttribute("lastViewed");
        if (lastViewed == null) {
            lastViewed = new LinkedList<Product>();
            session.setAttribute("lastViewed", lastViewed);
        }
    }

    public LastViewed(List<Product> lastViewed) {
        this.lastViewed = lastViewed;
    }

    public void add(Product product) {
        if (lastViewed.contains(product)) {
            return;
        }

        if (lastViewed.size() == 3) {
            lastViewed.remove(0);
            lastViewed.add(product);
            return;
        }

        lastViewed.add(product);
    }

    public List<Product> getLastViewed() {
        return lastViewed;
    }

    public void setLastViewed(List<Product> lastViewed) {
        this.lastViewed = lastViewed;
    }
}

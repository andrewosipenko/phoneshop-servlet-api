package com.es.phoneshop.model.recentviews;

import com.es.phoneshop.model.product.Product;
import lombok.NonNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

public class DefaultRecentViewService implements RecentViewService {
    private static final String RECENT_VIEW_SESSION_ATTRIBUTE = DefaultRecentViewService.class.getName() + ".recent_views";
    private static volatile DefaultRecentViewService instance;
    private static final int NUMBER_OF_PRODUCTS = 3;

    private DefaultRecentViewService() {

    }

    public static DefaultRecentViewService getInstance() {
        if (instance == null) {
            synchronized (DefaultRecentViewService.class) {
                if (instance == null) {
                    instance = new DefaultRecentViewService();
                }
            }
        }
        return instance;
    }

    @Override
    public RecentView getRecentView(@NonNull final HttpServletRequest request) {
        RecentView recentView = (RecentView) request.getSession().getAttribute(RECENT_VIEW_SESSION_ATTRIBUTE);
        if (recentView == null) {
            request.getSession().setAttribute(RECENT_VIEW_SESSION_ATTRIBUTE, recentView = new RecentView());
        }
        return recentView;
    }

    @Override
    public void add(@NonNull final RecentView recentView,@NonNull final Product product) {
        recentView.getRecentlyViewed().remove(product);

        recentView.getRecentlyViewed().add(product);
        Collections.rotate(recentView.getRecentlyViewed(), 1);

        if (recentView.getRecentlyViewed().size() == NUMBER_OF_PRODUCTS + 1) {
            recentView.getRecentlyViewed().remove(NUMBER_OF_PRODUCTS);
        }
    }
}

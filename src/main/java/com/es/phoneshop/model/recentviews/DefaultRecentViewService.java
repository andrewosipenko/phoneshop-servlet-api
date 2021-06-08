package com.es.phoneshop.model.recentviews;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public class DefaultRecentViewService implements RecentViewService {
    private static final String RECENT_VIEW_SESSION_ATTRIBUTE = DefaultRecentViewService.class.getName() + ".recent_views";
    private static DefaultRecentViewService instance;

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
    public RecentView getRecentView(HttpServletRequest request) {
        RecentView recentView = (RecentView) request.getSession().getAttribute(RECENT_VIEW_SESSION_ATTRIBUTE);
        if (recentView == null) {
            request.getSession().setAttribute(RECENT_VIEW_SESSION_ATTRIBUTE, recentView = new RecentView());
        }
        return recentView;
    }

    @Override
    public void add(RecentView recentView, Product product) {
        if (recentView.getRecentlyViewed().contains(product)) return;

        if (recentView.getRecentlyViewed().size() == 3) {
            recentView.getRecentlyViewed().remove(0);
        }

        recentView.getRecentlyViewed().add(product);
    }
}

package com.es.phoneshop.model.recentView;

import javax.servlet.http.HttpServletRequest;

public class HttpSessionRecentViewService implements RecentViewService {
    private static final String RECENT_VIEW_SESSION_ATTRIBUTE = HttpSessionRecentViewService.class.getName() + ".recentView";
    private static HttpSessionRecentViewService instance;

    public static synchronized HttpSessionRecentViewService getInstance() {
        if (instance == null) {
            instance = new HttpSessionRecentViewService();
        }
        return instance;
    }

    private HttpSessionRecentViewService() {}

    @Override
    public synchronized RecentView getRecentView(HttpServletRequest request) {
        RecentView recentView = (RecentView) request.getSession().getAttribute(RECENT_VIEW_SESSION_ATTRIBUTE);
        if(recentView == null) {
            recentView = new RecentView();
            request.getSession().setAttribute(RECENT_VIEW_SESSION_ATTRIBUTE, recentView);
        }
        return recentView;
    }
}

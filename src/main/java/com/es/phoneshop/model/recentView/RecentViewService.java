package com.es.phoneshop.model.recentView;

import javax.servlet.http.HttpServletRequest;

public interface RecentViewService {
    RecentView getRecentView(HttpServletRequest request);
}

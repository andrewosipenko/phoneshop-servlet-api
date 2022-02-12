package com.es.phoneshop.web;

import com.es.phoneshop.model.recentView.HttpSessionRecentViewService;
import com.es.phoneshop.model.recentView.RecentViewService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RecentViewServlet extends HttpServlet {
    private RecentViewService recentViewService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        recentViewService = HttpSessionRecentViewService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("recentView", recentViewService.getRecentView(request).getDeque());
        request.getRequestDispatcher("/WEB-INF/pages/recentView.jsp").include(request, response);
    }
}

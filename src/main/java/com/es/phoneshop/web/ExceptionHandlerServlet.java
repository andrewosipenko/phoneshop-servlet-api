package com.es.phoneshop.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        catchException(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        catchException(request, response);
    }

    private void catchException(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown Path Segment";
        }
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown Request URI";
        }
        StringBuilder msg = new StringBuilder();
        if (throwable == null || statusCode == null) {
            msg.append("Error information is missing");
        } else {
            msg.append("Error information\n");
            msg.append("Servlet Name: ").append(servletName).append("\n");
            msg.append("Exception Type: ").append(throwable.getClass().getName()).append("\n");
            msg.append("The request URI: ").append(requestUri).append("\n");
            msg.append("The exception message: ").append(throwable.getMessage() != null ?
                    throwable.getMessage() : "Message is missing");
        }
        request.setAttribute("errorMsg", msg.toString());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/error.jsp");
        requestDispatcher.forward(request, response);
    }
}

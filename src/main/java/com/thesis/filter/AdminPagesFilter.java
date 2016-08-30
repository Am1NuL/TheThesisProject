package com.thesis.filter;

import com.thesis.model.Account;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Alex on 21-Aug-16.
 */
public class AdminPagesFilter extends AbstractFilter implements Filter {
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Account user = (Account) req.getSession(true).getAttribute("user");

        if (user == null || !user.isAdmin()) {
            accessDenied(request, response, req);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}

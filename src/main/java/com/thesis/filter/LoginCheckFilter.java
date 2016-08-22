package com.thesis.filter;

import com.thesis.model.Account;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 21-Aug-16.
 */
public class LoginCheckFilter extends AbstractFilter implements Filter{
    private static List<String> allowedURIs;

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        if(allowedURIs == null){
            allowedURIs = new ArrayList<String>();
            allowedURIs.add(fConfig.getInitParameter("loginActionURI"));
            allowedURIs.add("/JSFCrudApp/javax.faces.resource/main.css.xhtml");
            allowedURIs.add("/JSFCrudApp/javax.faces.resource/theme.css.xhtml");
            allowedURIs.add("/JSFCrudApp/javax.faces.resource/primefaces.js.xhtml");
            allowedURIs.add("/JSFCrudApp/javax.faces.resource/primefaces.css.xhtml");
            allowedURIs.add("/JSFCrudApp/javax.faces.resource/messages/messages.png.xhtml");
            allowedURIs.add("/JSFCrudApp/javax.faces.resource/images/ui-icons_2e83ff_256x240.png.xhtml");
            allowedURIs.add("/JSFCrudApp/javax.faces.resource/images/ui-icons_38667f_256x240.png.xhtml");
        }
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        if (session.isNew()) {
            doLogin(request, response, req);
            return;
        }

        Account user = (Account) session.getAttribute("user");

        if (user == null && !allowedURIs.contains(req.getRequestURI())) {
            System.out.println(req.getRequestURI());
            doLogin(request, response, req);
            return;
        }

        chain.doFilter(request, response);
    }
}

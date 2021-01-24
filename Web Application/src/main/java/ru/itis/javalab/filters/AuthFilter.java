package ru.itis.javalab.filters;

import ru.itis.javalab.services.SecurityService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    SecurityService securityService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
        securityService = (SecurityService) context.getAttribute("securityService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if (securityService.isSigned(req)) {
            filterChain.doFilter(req, resp);
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/registration");
        }
    }

    @Override
    public void destroy() {

    }
}

package com.drk.tools.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author new on 14/07/20
 */
@Component
public class CORSFilter implements Filter {
    public CORSFilter() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException
    {

        HttpServletRequest request = (HttpServletRequest) req;

        HttpServletResponse response = (HttpServletResponse) res;
        System.err.println("AllowOrigin: " + request.getHeader("allow-origin"));

        response.setHeader("Access-Control-Allow-Origin", "*");// request.getHeader("allow-origin")
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type, Accept, X-Requested-With, authorization, Allow-Origin, Access-Control-Allow-Origin, remember-me");

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {
    }
}

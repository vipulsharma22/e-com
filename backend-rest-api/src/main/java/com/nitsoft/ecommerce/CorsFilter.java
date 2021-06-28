/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@Component
public class CorsFilter implements Filter {

    private static final Set<String> allowedOrigins = new HashSet<String>(Lists.newArrayList("localhost:3000","https://lil-munchkin.herokuapp.com","0:0:0:0:0:0:0:1"));
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        String originHeader = req.getRemoteHost();
        log.info("originHeader: " + originHeader);
        if(allowedOrigins.contains(originHeader)){
            response.setHeader("Access-Control-Allow-Origin", originHeader);
        }
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, X-Access-Token");
        chain.doFilter(req, response);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}

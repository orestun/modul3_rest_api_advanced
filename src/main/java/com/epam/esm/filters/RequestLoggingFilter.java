package com.epam.esm.filters;

import com.sun.net.httpserver.HttpServer;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.crypto.dsig.spec.XPathType;
import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        log.info(String.format("Request method: %s by URI: %s",
                httpServletRequest.getMethod(),
                httpServletRequest.getRequestURI()));
        filterChain.doFilter(
                servletRequest,
                servletResponse);
    }
}

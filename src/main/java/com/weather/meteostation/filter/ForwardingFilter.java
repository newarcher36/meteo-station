package com.weather.meteostation.filter;

import javax.inject.Named;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Pattern;

@Named
@WebFilter(urlPatterns = "/")
public class ForwardingFilter implements Filter {

    private final Pattern[] urlResourcePatterns = new Pattern[] {
            Pattern.compile("/catalog.*")
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            String path = ((HttpServletRequest) request).getServletPath();
            for (Pattern pattern : urlResourcePatterns) {
                if (pattern.matcher(path).matches()) {
                    request.getRequestDispatcher("/").forward(request, response);
                    return;
                }
             }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

package com.zcj.filters;

import com.sun.deploy.net.HttpResponse;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter",urlPatterns = "/*")
public class EncodingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request= (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //解决响应的乱码问题
        response.setContentType("text/html;charset=utf-8");
        //解决请求的乱码问题
        String method = request.getMethod();
        if("POST".equalsIgnoreCase(method)){
            request.setCharacterEncoding("utf-8");
        }
        //放行
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

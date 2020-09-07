package com.spark.ncms.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/api/v1/moh/*"})
public class MohFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(false);

        if(session!=null){
            String  role = (String) session.getAttribute("role");
            if(role.equals("moh")){
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                ((HttpServletResponse)servletResponse).sendError(401);
            }

        }else{

        }
    }
}

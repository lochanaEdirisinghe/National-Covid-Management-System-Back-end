package com.spark.ncms.filter;

import com.spark.ncms.constants.Constants;
import com.spark.ncms.util.JWTUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebFilter(urlPatterns = {"/api/v1/doctor", "/api/v1/moh/*" })
public class AuthFilter implements Filter {

    private JWTUtil jwtUtil = new JWTUtil();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String header = request.getHeader(Constants.HEADER_AUTHORIZATION);


        //check the token validity
        if (!jwtUtil.checkValidity(header)) {
            //if a request came without a token or without a valid token
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setStatus(401);
            out.println("{\"code\":401,\"message\":\"error\",\"data\":null}");
            out.flush();
            return;

        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}

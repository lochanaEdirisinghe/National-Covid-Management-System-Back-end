package com.spark.ncms.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.ncms.constants.ResponseCode;
import com.spark.ncms.dto.AuthUser;
import com.spark.ncms.response.StandardResponse;
import com.spark.ncms.service.ServiceFactory;
import com.spark.ncms.service.ServiceType;
import com.spark.ncms.service.custom.AuthService;
import com.spark.ncms.util.JWTUtil;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;


@WebServlet(urlPatterns = "/api/v1/auth/login")
public class AuthController extends HttpServlet {

    private AuthService authService;
    private JWTUtil jwtUtil = new JWTUtil();

    public AuthController() {
        authService = ServiceFactory.getInstance().getService(ServiceType.USER);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonObject = CommonMethods.getJsonObject(req);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String userId = jsonObject.getString("userId");
            String password = jsonObject.getString("password");

            BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
            try (Connection con = bds.getConnection()) {
                //check the user is available
                String role = authService.userCheck(userId, password, con);

                if (role != null) {
                    String token = jwtUtil.createToken(userId);
                    AuthUser authUser = new AuthUser(userId, password, role);
                    String responseJson = mapper.writeValueAsString(new StandardResponse(ResponseCode.SUCCESS, token, authUser));
                    CommonMethods.responseProcess(resp, responseJson);
                } else {
                    String responseJson = mapper.writeValueAsString(new StandardResponse(ResponseCode.UNAUTHORIZED, "UserId & Password is Wrong..!", null));
                    CommonMethods.responseProcess(resp, responseJson);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "serverError!!!"+e.getMessage());
        }
    }


}

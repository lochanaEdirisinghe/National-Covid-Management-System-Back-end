package com.spark.ncms.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.ncms.response.StandardResponse;
import com.spark.ncms.service.ServiceFactory;
import com.spark.ncms.service.ServiceType;
import com.spark.ncms.service.custom.AuthService;
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

    public AuthController() {
        authService = ServiceFactory.getInstance().getService(ServiceType.USER);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonObject = CommonMethods.getJsonObject(req);
        ObjectMapper mapper = new ObjectMapper();

        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        try {
            BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
            try (Connection con = bds.getConnection()) {
                String role = authService.userCheck(username, password, con);
                if (role != null) {
                    req.getSession().setAttribute("role", role);
                    String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", role));
                    CommonMethods.responseProcess(resp, responseJson);
                } else {
                    String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", "not allowed"));
                    CommonMethods.responseProcess(resp, responseJson);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;


        }
    }


}

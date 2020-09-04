package com.spark.ncms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.ncms.dto.HospitalDto;
import com.spark.ncms.dto.QueueDto;
import com.spark.ncms.response.StandardResponse;
import com.spark.ncms.service.MohService;
import com.spark.ncms.service.serviceImpl.MohServiceImpl;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

@WebServlet(urlPatterns = "/api/v1/moh")
public class MohController extends HttpServlet {

    MohService mohService = new MohServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
            try(Connection con = bds.getConnection()) {
                List<QueueDto> queuePatients = mohService.getQueueDetails(con);
                ObjectMapper mapper = new ObjectMapper();
                String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", queuePatients ));
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(responseJson);
                out.flush();
            }

        }catch (Exception e){
            ObjectMapper mapper = new ObjectMapper();
            String responseJson = mapper.writeValueAsString(new StandardResponse(500, "false", "an error occured"));
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(responseJson);
            out.flush();

            e.printStackTrace();

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletInputStream inputStream = req.getInputStream();
        JsonReader reader = Json.createReader(inputStream);
        JsonObject jsonObject = reader.readObject();

        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String district = jsonObject.getString("district");
        int locationX = jsonObject.getInt("locationX");
        int locationY = jsonObject.getInt("locationY");

        HospitalDto hospitalDto = new HospitalDto(id, name, district, locationX, locationY);

        try {
            BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
            try(Connection con = bds.getConnection()) {
                boolean isAdded = mohService.addNewHospital(hospitalDto, con);
                ObjectMapper mapper = new ObjectMapper();
                String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", isAdded ));
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(responseJson);
                out.flush();
            }

        }catch (Exception e){
            ObjectMapper mapper = new ObjectMapper();
            String responseJson = mapper.writeValueAsString(new StandardResponse(500, "false", "an error occured"));
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(responseJson);
            out.flush();

            e.printStackTrace();

        }



    }
}

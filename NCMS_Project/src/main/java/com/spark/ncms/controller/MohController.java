package com.spark.ncms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.ncms.dto.HospitalBedDto;
import com.spark.ncms.dto.HospitalDto;
import com.spark.ncms.dto.QueueDto;
import com.spark.ncms.response.StandardResponse;
import com.spark.ncms.service.ServiceFactory;
import com.spark.ncms.service.ServiceType;
import com.spark.ncms.service.custom.MohService;
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

@WebServlet(urlPatterns = "/api/v1/moh/*")
public class MohController extends HttpServlet {

    private MohService mohService;

    public MohController() {
        mohService = ServiceFactory.getInstance().getService(ServiceType.MOH);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        switch (req.getPathInfo()) {

            case "/queue":

                try {
                    BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
                    try (Connection con = bds.getConnection()) {
                        List<QueueDto> queuePatients = mohService.getQueueDetails(con);
                        ObjectMapper mapper = new ObjectMapper();
                        String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", queuePatients));
                        CommonMethods.responseProcess(resp, responseJson);
                    }

                } catch (Exception e) {
                    ObjectMapper mapper = new ObjectMapper();
                    String responseJson = mapper.writeValueAsString(new StandardResponse(500, "false", "an error occured"));
                    CommonMethods.responseProcess(resp, responseJson);
                    e.printStackTrace();
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;

                }

                break;

            case "/beds":

                try {
                    BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
                    try (Connection con = bds.getConnection()) {
                        List<HospitalBedDto> bedDetails = mohService.getBedDetails(con);
                        ObjectMapper mapper = new ObjectMapper();
                        String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", bedDetails));
                        CommonMethods.responseProcess(resp, responseJson);
                    }

                } catch (Exception e) {
                    ObjectMapper mapper = new ObjectMapper();
                    String responseJson = mapper.writeValueAsString(new StandardResponse(500, "false", "an error occured"));
                    CommonMethods.responseProcess(resp, responseJson);
                    e.printStackTrace();
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;

                }

            default:
                ObjectMapper mapper = new ObjectMapper();
                String responseJson = mapper.writeValueAsString(new StandardResponse(500, "false", "url is not valid"));
                CommonMethods.responseProcess(resp, responseJson);

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonObject = CommonMethods.getJsonObject(req);

        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String district = jsonObject.getString("district");
        int locationX = jsonObject.getInt("locationX");
        int locationY = jsonObject.getInt("locationY");

        HospitalDto hospitalDto = new HospitalDto(id, name, district, locationX, locationY);

        try {
            BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
            try (Connection con = bds.getConnection()) {
                boolean isAdded = mohService.addNewHospital(hospitalDto, con);
                ObjectMapper mapper = new ObjectMapper();
                String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", isAdded));
                CommonMethods.responseProcess(resp, responseJson);
            }

        } catch (Exception e) {
            ObjectMapper mapper = new ObjectMapper();
            /*String responseJson = mapper.writeValueAsString(new StandardResponse(500, "false", "an error occured"));
            CommonMethods.responseProcess(resp, responseJson);*/
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            return;

        }


    }
}

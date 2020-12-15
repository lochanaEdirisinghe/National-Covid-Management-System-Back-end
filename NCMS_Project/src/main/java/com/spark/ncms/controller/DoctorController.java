package com.spark.ncms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.ncms.dto.DoctorDto1;
import com.spark.ncms.dto.DoctorDto2;
import com.spark.ncms.response.HospitaBedResponse;
import com.spark.ncms.response.StandardResponse;
import com.spark.ncms.service.ServiceFactory;
import com.spark.ncms.service.ServiceType;
import com.spark.ncms.service.custom.DoctorService;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(urlPatterns = "/api/v1/doctor/*")
public class DoctorController extends HttpServlet {

    private DoctorService doctorService;

    public DoctorController() {
        doctorService = ServiceFactory.getInstance().getService(ServiceType.DOCTOR);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String doctorId = req.getParameter("doctorId");

        switch (req.getPathInfo()) {
            case "/check":
                try {
                    BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
                    try (Connection con = bds.getConnection()) {
                        boolean isdirector = doctorService.isDirector(doctorId, con);
                        ObjectMapper mapper = new ObjectMapper();
                        String responseJson = mapper.writeValueAsString(new StandardResponse(HttpServletResponse.SC_OK, "true",  isdirector));
                        CommonMethods.responseProcess(req, resp, responseJson);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;

                }
                break;

            case "/id":
                try {
                    BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
                    try (Connection con = bds.getConnection()) {
                        HospitaBedResponse hospitaBedResponse = doctorService.getHospitalBedList(doctorId, con);
                        ObjectMapper mapper = new ObjectMapper();
                        String responseJson = mapper.writeValueAsString(new StandardResponse(HttpServletResponse.SC_OK, "true",  hospitaBedResponse));
                        CommonMethods.responseProcess(req,resp, responseJson);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;

                }

                break;

            case "/hospital":
                try {
                    String hospitalId = req.getParameter("hospitalId");
                    BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
                    try (Connection con = bds.getConnection()) {
                        List<DoctorDto1> allDoctors = doctorService.getAllDoctors(hospitalId, con);
                        ObjectMapper mapper = new ObjectMapper();
                        String responseJson = mapper.writeValueAsString(new StandardResponse(HttpServletResponse.SC_OK, "true",  allDoctors));
                        CommonMethods.responseProcess(req,resp, responseJson);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;

                }

                break;


            case "/all":
                try {
                    BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
                    try (Connection con = bds.getConnection()) {
                        List<DoctorDto2> allDoctors = doctorService.getAllDoctors(con);
                        ObjectMapper mapper = new ObjectMapper();
                        String responseJson = mapper.writeValueAsString(new StandardResponse(HttpServletResponse.SC_OK, "true",  allDoctors));
                        CommonMethods.responseProcess(req,resp, responseJson);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;

                }

        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String hospitalId = req.getParameter("hospitalId");
        String doctorId = req.getParameter("doctorId");
        try {
            BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
            try (Connection con = bds.getConnection()) {
                boolean isUpdated = doctorService.updateDoctor(doctorId, hospitalId, con);
                ObjectMapper mapper = new ObjectMapper();
                String responseJson = mapper.writeValueAsString(new StandardResponse(HttpServletResponse.SC_OK, "true",  isUpdated));
                CommonMethods.responseProcess(req,resp, responseJson);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            return;

        }

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hospitalId = req.getParameter("hospitalId");
        String doctorId = req.getParameter("doctorId");
        try {
            BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
            try (Connection con = bds.getConnection()) {
                boolean isUpdated = doctorService.updateDoctor(doctorId, hospitalId, con);
                ObjectMapper mapper = new ObjectMapper();
                String responseJson = mapper.writeValueAsString(new StandardResponse(HttpServletResponse.SC_OK, "true",  isUpdated));
                CommonMethods.responseProcess(req,resp, responseJson);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            return;

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonObject jsonObject = CommonMethods.getJsonObject(req);
        String doctorId = jsonObject.getString("doctorId");
        String doctorName = jsonObject.getString("name");
        int contactNo = Integer.parseInt(jsonObject.getString("contactNo"));
        String hospitalId = jsonObject.getString("hospital");
        boolean isDirector = Boolean.parseBoolean(jsonObject.getString("director"));

        DoctorDto1 doctorDto1 = new DoctorDto1(doctorId, doctorName, hospitalId,isDirector, contactNo);
        try {
            BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
            try (Connection con = bds.getConnection()) {
                boolean isAdded = doctorService.saveDoctor(doctorDto1, con);
                ObjectMapper mapper = new ObjectMapper();
                String responseJson = mapper.writeValueAsString(new StandardResponse(HttpServletResponse.SC_OK, "true",  isAdded));
                CommonMethods.responseProcess(req,resp, responseJson);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            return;

        }
    }
}

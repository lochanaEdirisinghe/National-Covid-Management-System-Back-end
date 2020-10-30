package com.spark.ncms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.ncms.dto.HospitalCount;
import com.spark.ncms.dto.PatientCount;
import com.spark.ncms.dto.PatientDto;
import com.spark.ncms.service.ServiceFactory;
import com.spark.ncms.service.ServiceType;
import com.spark.ncms.service.custom.MohService;
import com.spark.ncms.service.custom.PatientService;
import com.spark.ncms.response.PatientResponse;
import com.spark.ncms.response.StandardResponse;
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
import java.sql.Connection;
import java.util.List;

@WebServlet(urlPatterns = "/api/v1/patient/*")
public class PatientController extends HttpServlet {

    private PatientService patientService;


    public PatientController() {
        patientService = ServiceFactory.getInstance().getService(ServiceType.PATIENT);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonObject jsonObject = CommonMethods.getJsonObject(req);
        String firstName = jsonObject.getString("firstName");
        String lastName = jsonObject.getString("lastName");
        int locationX = Integer.parseInt(jsonObject.getString("locationX"));
        int locationY = Integer.parseInt(jsonObject.getString("locationY"));
        String district = jsonObject.getString("district");
        int age = Integer.parseInt(jsonObject.getString("age"));
        String gender = jsonObject.getString("gender");
        String contactNo = jsonObject.getString("contactNo");
        String email = jsonObject.getString("email");


        PatientDto patientDto = new PatientDto();
        patientDto.setFirstName(firstName);
        patientDto.setLastName(lastName);
        patientDto.setAge(age);
        patientDto.setLocationX(locationX);
        patientDto.setLocationY(locationY);
        patientDto.setDistrict(district);
        patientDto.setGender(gender);
        patientDto.setContactNo(contactNo);
        patientDto.setEmail(email);


        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");

        try {

            try (Connection con = bds.getConnection()) {
                PatientResponse patientResponse = patientService.savePatient(patientDto, con);
                ObjectMapper mapper = new ObjectMapper();
                String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", patientResponse));
                CommonMethods.responseProcess(req,resp, responseJson);
            }


        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        switch (req.getPathInfo()) {
            case "/id":
                String patientId = req.getParameter("patientId");
                try {
                    BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
                    try (Connection con = bds.getConnection()) {
                        PatientDto patient = patientService.getPatient(patientId, con);
                        ObjectMapper mapper = new ObjectMapper();
                        String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", patient));
                        CommonMethods.responseProcess(req,resp, responseJson);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;

                }

                break;


            case "/totalcount":
                try {
                    BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
                    try (Connection con = bds.getConnection()) {
                        PatientCount patientCount = patientService.getPatientCount(con);
                        ObjectMapper mapper = new ObjectMapper();
                        String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", patientCount));
                        CommonMethods.responseProcess(req,resp, responseJson);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;

                }

                break;

            case "/hospitalcount":
                try {
                    BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
                    try (Connection con = bds.getConnection()) {
                        List<HospitalCount> bedCount = patientService.getHospitalPatientCount(con);
                        ObjectMapper mapper = new ObjectMapper();
                        String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", bedCount));
                        CommonMethods.responseProcess(req,resp, responseJson);
                    }

                } catch (Exception e) {
                    ObjectMapper mapper = new ObjectMapper();
                    String responseJson = mapper.writeValueAsString(new StandardResponse(500, "false", "an error occured"));
                    CommonMethods.responseProcess(req,resp, responseJson);
                    e.printStackTrace();
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;

                }
                break;


            case "/":
                try {
                    BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
                    try (Connection con = bds.getConnection()) {
                        List<PatientDto> patients = patientService.getAllPatient(con);
                        ObjectMapper mapper = new ObjectMapper();
                        String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", patients));
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

        String patientId = req.getParameter("patientId");
        String doctorId = req.getParameter("doctorId");
        String slevel = req.getParameter("slevel");
        String doctorRole = req.getParameter("doctorRole"); //addmit or discharged

        try {
            BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
            try (Connection con = bds.getConnection()) {
                boolean isUpdated = patientService.updatePatient(patientId, doctorId, slevel, doctorRole, con);
                ObjectMapper mapper = new ObjectMapper();
                if (isUpdated) {
                    String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", isUpdated));
                    CommonMethods.responseProcess(req,resp, responseJson);
                } else if (!isUpdated) {
                    String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", isUpdated));
                    CommonMethods.responseProcess(req,resp, responseJson);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            return;

        }
    }
}

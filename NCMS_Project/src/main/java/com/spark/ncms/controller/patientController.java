package com.spark.ncms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.ncms.dto.PatientDto;
import com.spark.ncms.service.ServiceFactory;
import com.spark.ncms.service.ServiceType;
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
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

@WebServlet(urlPatterns = "/api/v1/patient/*")
public class patientController extends HttpServlet {

    private PatientService patientService;

    public patientController(){
        patientService=ServiceFactory.getInstance().getService(ServiceType.PATIENT);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletInputStream inputStream = req.getInputStream();
        JsonReader reader = Json.createReader(inputStream);
        JsonObject jsonObject = reader.readObject();

        String firstName = jsonObject.getString("firstName");
        String lastName = jsonObject.getString("lastName");
        int locationX = jsonObject.getInt("locationX");
        int locationY = jsonObject.getInt("locationY");
        String district = jsonObject.getString("district");
        int age = jsonObject.getInt("age");
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

            try(Connection con = bds.getConnection()) {
                PatientResponse patientResponse = patientService.savePatient(patientDto, con);
                ObjectMapper mapper = new ObjectMapper();
                String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", patientResponse));
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!req.getPathInfo().equals("/")) {
            String patientId = req.getParameter("patientId");
            try {
                BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
                try (Connection con = bds.getConnection()) {
                    PatientDto patient = patientService.getPatient(patientId, con);
                    ObjectMapper mapper = new ObjectMapper();
                    String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", patient));
                    PrintWriter out = resp.getWriter();
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    out.print(responseJson);
                    out.flush();
                }

            } catch (Exception e) {
                ObjectMapper mapper = new ObjectMapper();
                String responseJson = mapper.writeValueAsString(new StandardResponse(500, "false", "an error occured"));
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(responseJson);
                out.flush();

                e.printStackTrace();

            }
        } else if (req.getPathInfo().equals("/")) {
            //String patientId = req.getParameter("patientId");
            try {
                BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
                try (Connection con = bds.getConnection()) {
                    List<PatientDto> patients = patientService.getAllPatient(con);
                    ObjectMapper mapper = new ObjectMapper();
                    String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", patients));
                    PrintWriter out = resp.getWriter();
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    out.print(responseJson);
                    out.flush();
                }

            } catch (Exception e) {
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
}

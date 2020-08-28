package com.spark.ncms.controller;

import com.spark.ncms.dto.PatientDto;
import com.spark.ncms.service.PatientService;
import com.spark.ncms.service.serviceImpl.PatientServiceImpl;
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

@WebServlet(urlPatterns = "/api/v1/register")
public class patientController extends HttpServlet {
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
        PatientService patientService= new PatientServiceImpl();
        try {
            PatientResponse patientResponse = patientService.savePatient(patientDto, bds);
            new StandardResponse(200, "true", patientResponse);
        }catch (Exception e){
            new StandardResponse(500, "false", "an error occured" );
        }

    }
}

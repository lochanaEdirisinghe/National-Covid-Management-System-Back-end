package com.spark.ncms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.ncms.response.HospitaBedResponse;
import com.spark.ncms.response.StandardResponse;
import com.spark.ncms.service.ServiceFactory;
import com.spark.ncms.service.ServiceType;
import com.spark.ncms.service.custom.DoctorService;
import com.spark.ncms.service.custom.impl.DoctorServiceImpl;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet(urlPatterns = "/api/v1/Doctor/*")
public class DoctorController extends HttpServlet {

    private DoctorService doctorService;

    public DoctorController(){
        doctorService=ServiceFactory.getInstance().getService(ServiceType.DOCTOR);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getPathInfo());
        String doctorId = req.getParameter("doctorId");

        try {
            BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
            try(Connection con = bds.getConnection()) {
                HospitaBedResponse hospitaBedResponse = doctorService.getHospitalBedList(doctorId, con);
                ObjectMapper mapper = new ObjectMapper();
                String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", hospitaBedResponse));
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


    //patient admittedby and dischargedby update method
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String patientId = req.getParameter("patientId");
        String doctorId = req.getParameter("doctorId");
        String doctorRole = req.getParameter("doctorRole"); //addmit or discharged

        try {
            BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
            try(Connection con = bds.getConnection()) {
                boolean isUpdated = doctorService.updatePatient(patientId, doctorId, doctorRole, con);
                ObjectMapper mapper = new ObjectMapper();
                if(isUpdated){
                    String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", "patient is updated" ));
                    PrintWriter out = resp.getWriter();
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    out.print(responseJson);
                    out.flush();
                }else if(!isUpdated){
                    String responseJson = mapper.writeValueAsString(new StandardResponse(200, "true", "patient is not updated" ));
                    PrintWriter out = resp.getWriter();
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    out.print(responseJson);
                    out.flush();
                }
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

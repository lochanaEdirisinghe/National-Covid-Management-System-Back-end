package com.spark.ncms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.ncms.response.HospitaBedResponse;
import com.spark.ncms.response.StandardResponse;
import com.spark.ncms.service.ServiceFactory;
import com.spark.ncms.service.ServiceType;
import com.spark.ncms.service.custom.DoctorService;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(urlPatterns = "/api/v1/doctor")
public class DoctorController extends HttpServlet {

    private DoctorService doctorService;

    public DoctorController() {
        doctorService = ServiceFactory.getInstance().getService(ServiceType.DOCTOR);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String doctorId = req.getParameter("doctorId");
        System.out.println(req.getHeader("Authorization"));
        System.out.println(doctorId + "doc servlet");

        try {
            BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute("db");
            try (Connection con = bds.getConnection()) {
                HospitaBedResponse hospitaBedResponse = doctorService.getHospitalBedList(doctorId, con);
                System.out.println(hospitaBedResponse.getHospitalName());
                ObjectMapper mapper = new ObjectMapper();
                String responseJson = mapper.writeValueAsString(new StandardResponse(HttpServletResponse.SC_OK, "true",  hospitaBedResponse));
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

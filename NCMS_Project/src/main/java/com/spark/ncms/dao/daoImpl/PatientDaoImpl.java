package com.spark.ncms.dao.daoImpl;


import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatientDaoImpl implements com.spark.ncms.dao.PatientDao {
    @Override
    public ArrayList<String> BedsAvailableHospitals(BasicDataSource bds) {
        try {
            ArrayList<String> hospitals = new ArrayList<>();
            Connection con = bds.getConnection();
            PreparedStatement pstm = con.prepareStatement("select distinct hospital_id from hospital_bed where patient_id is null");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()){
                hospitals.add(rst.getString(1));
            }

            return hospitals;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

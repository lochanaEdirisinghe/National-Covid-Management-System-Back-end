package com.spark.ncms.repository.custom.impl;

import com.spark.ncms.entity.Hospital;
import com.spark.ncms.repository.custom.HospitalRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HospitalRepoImpl implements HospitalRepository {

    @Override
    public boolean addNewHospital(Hospital hospital, Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("insert into hospital values(?,?,?,?,?)");
        pstm.setObject(1, hospital.getId());
        pstm.setObject(2, hospital.getName());
        pstm.setObject(3, hospital.getDistrict());
        pstm.setObject(4, hospital.getLocation_x());
        pstm.setObject(5, hospital.getLocation_y());
        int i = pstm.executeUpdate();
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Hospital getHospital(String id, Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("select * from hospital where id=?");
        pstm.setObject(1, id);
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            return new Hospital(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getInt(5));
        }
        return null;
    }

    @Override
    public List<Hospital> getAllHospitals(Connection con) throws SQLException, ClassNotFoundException {
        List<Hospital> hospitalList = new ArrayList<>();
        PreparedStatement pstm = con.prepareStatement("select * from hospital");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            hospitalList.add(new Hospital(rst.getString(1), rst.getString(2), rst.getString(3),
                    rst.getInt(4), rst.getInt(5)));

        }
        return hospitalList;
    }
}

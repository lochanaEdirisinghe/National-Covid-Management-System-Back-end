package com.spark.ncms.repository.custom.impl;

import com.spark.ncms.dto.DoctorDto1;
import com.spark.ncms.entity.Doctor;
import com.spark.ncms.repository.custom.DoctorRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepoImpl implements DoctorRepository {
    @Override
    public String getHospitalId(String doctorId, Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("select hospital_id from doctor where id =?");
        pstm.setObject(1, doctorId);
        ResultSet rst = pstm.executeQuery();
        if(rst.next()){
            return rst.getString(1);
        }

        return null;
    }

    @Override
    public boolean isDirector(String doctorId, Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("select is_director from doctor where id =?");
        pstm.setObject(1, doctorId);
        ResultSet rst = pstm.executeQuery();
        if(rst.next()){
            return rst.getBoolean(1);
        }

        return false;
    }

    @Override
    public boolean updateDoctor(String doctorId, String hospitalId, Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("update doctor set hospital_id=? , is_director=? where id =?");
        pstm.setObject(1, hospitalId);
        pstm.setObject(2, true);
        pstm.setObject(3, doctorId);
        int i = pstm.executeUpdate();
        if (i>0){
            return true;
        }

        return false;
    }

    @Override
    public List<Doctor> getDoctorList(String hospitalId, Connection con) throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        PreparedStatement pstm = con.prepareStatement("select * from doctor where hospital_id=?");
        pstm.setObject(1, hospitalId);
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            doctors.add(new Doctor(rst.getString(1), rst.getString(2),rst.getString(3),
                    rst.getBoolean(4), rst.getInt(5)));
        }
        return doctors;
    }

    @Override
    public boolean saveDotor(DoctorDto1 doctorDto1, Connection con) throws SQLException {
        PreparedStatement pstm = con.prepareStatement("insert into doctor values(?,?,? ,?,?)");
        pstm.setObject(1, doctorDto1.getId());
        pstm.setObject(2, doctorDto1.getName());
        pstm.setObject(3, doctorDto1.getHospitalId());
        pstm.setObject(4, doctorDto1.isDirector());
        pstm.setObject(5, doctorDto1.getContactNo());
        int i = pstm.executeUpdate();
        if (i>0){
            return true;
        }

        return false;
    }

    @Override
    public List<Doctor> getDoctorList(Connection con) throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        PreparedStatement pstm = con.prepareStatement("select * from doctor");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            doctors.add(new Doctor(rst.getString(1), rst.getString(2),rst.getString(3),
                    rst.getBoolean(4), rst.getInt(5)));
        }
        return doctors;
    }

}

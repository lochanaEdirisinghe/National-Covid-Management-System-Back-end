package com.spark.ncms.repository.custom.impl;

import com.spark.ncms.repository.custom.DoctorRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}

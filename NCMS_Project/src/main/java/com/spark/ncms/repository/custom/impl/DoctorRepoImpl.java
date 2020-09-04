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

}

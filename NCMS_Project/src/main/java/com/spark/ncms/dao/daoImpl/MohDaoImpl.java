package com.spark.ncms.dao.daoImpl;

import com.spark.ncms.dao.MohDao;
import com.spark.ncms.dto.HospitalDto;
import com.spark.ncms.dto.QueueDto;
import com.spark.ncms.entity.Hospital;
import com.spark.ncms.entity.PatientQueue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MohDaoImpl implements MohDao {
    @Override
    public List<PatientQueue> getQueuePatients(Connection con) throws SQLException, ClassNotFoundException{
        List<PatientQueue> queueList = new ArrayList<>();
        PreparedStatement pstm = con.prepareStatement("select * from patient_queue");
        ResultSet rst = pstm.executeQuery();
        if(rst.next()){
            while (rst.next()){
                queueList.add(new PatientQueue(rst.getInt(1), rst.getString(2)));
            }

            return queueList;
        }
        return null;
    }

    @Override
    public boolean addNewHospital(Hospital hospital, Connection con) throws SQLException, ClassNotFoundException{
        PreparedStatement pstm = con.prepareStatement("insert into hospital values(?,?,?,?,?)");
        pstm.setObject(1, hospital.getId());
        pstm.setObject(2, hospital.getName());
        pstm.setObject(3, hospital.getDistrict());
        pstm.setObject(4, hospital.getLocation_x());
        pstm.setObject(5, hospital.getLocation_y());
        int i = pstm.executeUpdate();
        if (i>0){
            return true;
        }
        return false;
    }
}

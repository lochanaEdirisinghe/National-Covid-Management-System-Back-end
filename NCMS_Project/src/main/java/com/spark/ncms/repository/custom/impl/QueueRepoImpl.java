package com.spark.ncms.repository.custom.impl;

import com.spark.ncms.entity.PatientQueue;
import com.spark.ncms.repository.custom.QueueRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueueRepoImpl implements QueueRepository {
    @Override
    public boolean addToQueue(String patientId, Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("insert into patient_queue(patient_id) values (?)");
        pstm.setObject(1, patientId);
        if(pstm.executeUpdate()>0){
            return true;
        }
        return false;
    }

    @Override
    public int getQueueNo(String patientId, Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("select id from patient_queue where patient_id = ?");
        pstm.setObject(1, patientId);
        ResultSet rst = pstm.executeQuery();
        if(rst.next()){
            return rst.getInt(1);
        }
        return 0;
    }

    @Override
    public List<PatientQueue> getQueuePatients(Connection con) throws SQLException, ClassNotFoundException {
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
}

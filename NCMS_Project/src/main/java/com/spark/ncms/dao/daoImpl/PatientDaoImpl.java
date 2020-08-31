package com.spark.ncms.dao.daoImpl;


import com.spark.ncms.entity.Hospital;
import com.spark.ncms.entity.Patient;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatientDaoImpl implements com.spark.ncms.dao.PatientDao {
    @Override
    public ArrayList<String> BedsAvailableHospitals(Connection con)throws SQLException, ClassNotFoundException {
            ArrayList<String> hospitals = new ArrayList<>();
            PreparedStatement pstm = con.prepareStatement("select distinct hospital_id from hospital_bed where patient_id is null");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()){
                hospitals.add(rst.getString(1));
            }
            return hospitals;
    }

    @Override
    public boolean savePatient(Patient patient, Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("insert into patient values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        pstm.setObject(1, patient.getId());
        pstm.setObject(2, patient.getFirstName());
        pstm.setObject(3, patient.getLastName());
        pstm.setObject(4, patient.getDistrict());
        pstm.setObject(5, patient.getLocationX());
        pstm.setObject(6, patient.getLocationY());
        pstm.setObject(7, patient.getSeverity_level());
        pstm.setObject(8, patient.getGender());
        pstm.setObject(9, patient.getContact());
        pstm.setObject(10, patient.getEmail());
        pstm.setObject(11, patient.getAge());
        pstm.setObject(12, patient.getAdmit_date());
        pstm.setObject(13, patient.getAdmitted_by());
        pstm.setObject(14, patient.getDischarge_date());
        pstm.setObject(15, patient.getDischarged_by());

        if(pstm.executeUpdate()>0) {
            return true;
        }
        return false;
    }

    @Override
    public Hospital getHospital(String id, Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("select * from hospital where id=?");
        pstm.setObject(1, id);
        ResultSet rst = pstm.executeQuery();
        if(rst.next()){
            return new Hospital(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getInt(5));
        }
        //return new Hospital(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getInt(5));
        return null;
    }

    @Override
    public boolean patientBedUpdate(String hospitalId, int bedId, String patientId, Connection con)throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("update hospital_bed set patient_id =? where hospital_id = ? and id =?");
        pstm.setObject(1, patientId);
        pstm.setObject(2, hospitalId);
        pstm.setObject(3, bedId);

        if(pstm.executeUpdate()>0){
            return true;

        }
        return false;
    }

    @Override
    public int getBedId(String hospitalId, Connection con) throws SQLException, ClassNotFoundException{
        PreparedStatement pstm = con.prepareStatement("select min(id) from hospital_bed where hospital_id =? and patient_id is null");
        pstm.setObject(1, hospitalId);
        ResultSet rst = pstm.executeQuery();
        if(rst.next()){
            int bedId = rst.getInt(1);
            return bedId;
        }

        return 0;
    }

    @Override
    public boolean addToQueue(String patientId, Connection con)throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("insert into patient_queue(patient_id) values (?)");
        pstm.setObject(1, patientId);
        if(pstm.executeUpdate()>0){
            return true;
        }
        return false;
    }

    @Override
    public int getQueueNo(String patientId, Connection con) throws SQLException, ClassNotFoundException{
        PreparedStatement pstm = con.prepareStatement("select id from patient_queue where patient_id = ?");
        pstm.setObject(1, patientId);
        ResultSet rst = pstm.executeQuery();
        if(rst.next()){
            return rst.getInt(1);
        }
        return 0;
    }


}

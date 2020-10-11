package com.spark.ncms.repository.custom.impl;


import com.spark.ncms.dto.PatientCount;
import com.spark.ncms.entity.Patient;
import com.spark.ncms.repository.custom.PatientRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PatientRepoImpl implements PatientRepository {

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

        if (pstm.executeUpdate() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePatient(String patientId, String doctorId, String doctorRole, Connection con) throws SQLException, ClassNotFoundException {
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        if (doctorRole.equals("admit")) {
            PreparedStatement pstm = con.prepareStatement("update patient set admitted_by=?, admit_date=? where id=?");
            pstm.setObject(1, doctorId);
            pstm.setObject(2, date);
            pstm.setObject(3, patientId);
            int i = pstm.executeUpdate();
            if (i > 0) {
                return true;
            }
            return false;
        } else if (doctorRole.equals("discharge")) {
            if (getPatient(patientId, con).getAdmitted_by() != null) {
                PreparedStatement pstm = con.prepareStatement("update patient set discharged_by=?, discharge_date=? where id=?");
                pstm.setObject(1, doctorId);
                pstm.setObject(2, date);
                pstm.setObject(3, patientId);
                int i = pstm.executeUpdate();
                if (i > 0) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public List<Patient> getAllPatient(Connection con) throws SQLException, ClassNotFoundException {
        List<Patient> patients = new ArrayList<>();
        PreparedStatement pstm = con.prepareStatement("select * from patient");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            patients.add(new Patient(rst.getString(1), rst.getString(2), rst.getString(3),
                    rst.getString(4), rst.getInt(5), rst.getInt(6), rst.getString(7),
                    rst.getString(8), rst.getString(9), rst.getString(10), rst.getInt(11),
                    rst.getString(12), rst.getString(13), rst.getString(14), rst.getString(15)));

        }
        return patients;

    }


    @Override
    public Patient getPatient(String patientId, Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("select * from patient where id=?");
        pstm.setObject(1, patientId);
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            return new Patient(rst.getString(1), rst.getString(2), rst.getString(3),
                    rst.getString(4), rst.getInt(5), rst.getInt(6), rst.getString(7),
                    rst.getString(8), rst.getString(9), rst.getString(10), rst.getInt(11),
                    rst.getString(12), rst.getString(13), rst.getString(14), rst.getString(15));
        }
        return null;
    }

    @Override
    public PatientCount getPatientCount(Connection con) throws SQLException, ClassNotFoundException {
        PatientCount patientCount= new PatientCount();
        PreparedStatement pstm = con.prepareStatement("select count(id) from patient where discharge_date is null");
        ResultSet rst = pstm.executeQuery();
        if(rst.next()){
            patientCount.setActivecount(rst.getInt(1));
        }

        PreparedStatement pstm2 = con.prepareStatement("select count(id) from patient where discharge_date is not null");
        ResultSet rst2 = pstm2.executeQuery();
        if (rst2.next()) {
            patientCount.setDischarged(rst2.getInt(1));

        }
        return patientCount;

    }
/*
    @Override
    public PatientCount getDischargedPatientCount(Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("select count(id) from patient where discharge_date is not null");
        ResultSet rst2 = pstm.executeQuery();
        if (rst.next()) {
            patientCount.setDischarged(rst2.getInt(1));

        }
        return patientCount;
    }*/


}

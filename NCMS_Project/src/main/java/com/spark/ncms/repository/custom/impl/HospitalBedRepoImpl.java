package com.spark.ncms.repository.custom.impl;

import com.spark.ncms.entity.HospitalBed;
import com.spark.ncms.repository.custom.HospitalBedRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HospitalBedRepoImpl implements HospitalBedRepository {

    @Override
    public List<String> BedsAvailableHospitals(Connection con) throws SQLException, ClassNotFoundException {
        List<String> hospitals = new ArrayList<>();
        PreparedStatement pstm = con.prepareStatement("select distinct hospital_id from hospital_bed where patient_id is null");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            hospitals.add(rst.getString(1));
        }
        return hospitals;
    }

    @Override
    public List<HospitalBed> getHospitalBedList(String hospitalId, Connection con) throws SQLException, ClassNotFoundException {
        List<HospitalBed> hospital_beds = new ArrayList<>();
        PreparedStatement pstm = con.prepareStatement("select * from hospital_bed where hospital_id=? and patient_id is not null");
        pstm.setObject(1, hospitalId);
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            hospital_beds.add(new HospitalBed(rst.getInt(1), rst.getString(2), rst.getString(3)));
        }
        return hospital_beds;

    }

    @Override
    public int getBedCount(String hospitalId, Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("select count(id) from hospital_bed where hospital_id=? and patient_id is null");
        pstm.setObject(1, hospitalId);
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }

    @Override
    public boolean updateHospitalBed(String patientId, Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("update hospital_bed set patient_id=? where patient_id=?");
        pstm.setObject(1, null);
        pstm.setObject(2, patientId);
        int i = pstm.executeUpdate();
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int getBedId(String hospitalId, Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("select min(id) from hospital_bed where hospital_id =? and patient_id is null");
        pstm.setObject(1, hospitalId);
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            int bedId = rst.getInt(1);
            return bedId;
        }

        return 0;
    }

    @Override
    public boolean patientBedUpdate(String hospitalId, int bedId, String patientId, Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("update hospital_bed set patient_id =? where hospital_id = ? and id =?");
        pstm.setObject(1, patientId);
        pstm.setObject(2, hospitalId);
        pstm.setObject(3, bedId);

        if (pstm.executeUpdate() > 0) {
            return true;

        }
        return false;
    }

    @Override
    public boolean addHospitalBed(String hospitalId, Connection con) throws SQLException, ClassNotFoundException {
        for (int i = 1; i < 11; i++) {
            PreparedStatement pstm = con.prepareStatement("insert into hospital_bed values(? ,? , null)");
            pstm.setObject(1, i);
            pstm.setObject(2, hospitalId);
            pstm.executeUpdate();
        }
        return true;
    }
}

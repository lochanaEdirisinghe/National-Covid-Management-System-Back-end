package com.spark.ncms.dao;


import com.spark.ncms.entity.HospitalBed;
import com.spark.ncms.entity.Patient;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DoctorDao {
    String getHospitalId(String doctorId, Connection con) throws SQLException, ClassNotFoundException;
    List<HospitalBed> getHospitalBedList(String hospitalId, Connection con) throws SQLException, ClassNotFoundException;
    boolean updatePatient(String patientId, String doctorId, String doctorRole, Connection con) throws SQLException, ClassNotFoundException;
    boolean updateHospitalBed(String patientId, Connection con) throws SQLException, ClassNotFoundException;

}

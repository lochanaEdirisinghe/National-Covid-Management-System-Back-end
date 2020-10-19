package com.spark.ncms.repository.custom;

import com.spark.ncms.dto.PatientCount;
import com.spark.ncms.entity.Patient;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PatientRepository extends SuperRepository {

    boolean savePatient(Patient patient ,Connection con) throws SQLException, ClassNotFoundException;
    boolean updatePatient(String patientId, String doctorId, String slevel, String doctorRole, Connection con) throws SQLException, ClassNotFoundException;
    List<Patient> getAllPatient(Connection con)throws SQLException, ClassNotFoundException;
    Patient getPatient(String patientId, Connection con) throws SQLException, ClassNotFoundException;
    PatientCount getPatientCount(Connection con)throws SQLException, ClassNotFoundException;


}

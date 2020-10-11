package com.spark.ncms.service.custom;

import com.spark.ncms.dto.HospitalCount;
import com.spark.ncms.dto.PatientCount;
import com.spark.ncms.dto.PatientDto;
import com.spark.ncms.response.PatientResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PatientService extends SuperSevice {
    PatientResponse savePatient(PatientDto patientDto, Connection con) throws SQLException, SQLException, ClassNotFoundException;
    PatientDto getPatient(String patientId, Connection con) throws SQLException, ClassNotFoundException;
    List<PatientDto> getAllPatient(Connection con) throws SQLException, ClassNotFoundException;
    boolean updatePatient(String patientId, String doctorId, String doctorRole, Connection con) throws SQLException, ClassNotFoundException;
    PatientCount getPatientCount(Connection con)throws SQLException, ClassNotFoundException;
    List<HospitalCount> getHospitalPatientCount(Connection con)throws SQLException, ClassNotFoundException;
}

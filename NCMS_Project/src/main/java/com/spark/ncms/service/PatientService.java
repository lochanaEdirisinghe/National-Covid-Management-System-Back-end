package com.spark.ncms.service;

import com.spark.ncms.dto.PatientDto;
import com.spark.ncms.response.PatientResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public interface PatientService {
    PatientResponse savePatient(PatientDto patientDto, Connection con) throws SQLException, SQLException, ClassNotFoundException;
}

package com.spark.ncms.service;


import com.spark.ncms.dto.PatientDto;
import com.spark.ncms.response.HospitaBedResponse;

import java.sql.Connection;
import java.sql.SQLException;


public interface DoctorService {
    HospitaBedResponse getHospitalBedList(String doctorId, Connection con) throws SQLException, ClassNotFoundException;
    boolean updatePatient(String patientId, String doctorId, String doctorRole, Connection con) throws SQLException, ClassNotFoundException;
}

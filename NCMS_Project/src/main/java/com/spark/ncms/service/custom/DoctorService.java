package com.spark.ncms.service.custom;


import com.spark.ncms.dto.PatientDto;
import com.spark.ncms.response.HospitaBedResponse;

import java.sql.Connection;
import java.sql.SQLException;


public interface DoctorService extends SuperSevice{
    HospitaBedResponse getHospitalBedList(String doctorId, Connection con) throws SQLException, ClassNotFoundException;

}

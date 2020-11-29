package com.spark.ncms.service.custom;


import com.spark.ncms.dto.DoctorDto;
import com.spark.ncms.dto.DoctorDto2;
import com.spark.ncms.response.HospitaBedResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface DoctorService extends SuperSevice{
    HospitaBedResponse getHospitalBedList(String doctorId, Connection con) throws SQLException, ClassNotFoundException;
    boolean isDirector(String doctorId, Connection con) throws SQLException, ClassNotFoundException;
    boolean updateDoctor(String doctorId, String hospitalId, Connection con)throws SQLException, ClassNotFoundException;
    List<DoctorDto> getAllDoctors(String hospitalId, Connection con) throws SQLException;
    boolean saveDoctor(DoctorDto doctorDto, Connection con) throws SQLException;
    List<DoctorDto2> getAllDoctors(Connection con) throws SQLException;
}

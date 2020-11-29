package com.spark.ncms.repository.custom;


import com.spark.ncms.dto.DoctorDto;
import com.spark.ncms.entity.Doctor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DoctorRepository extends SuperRepository{
    String getHospitalId(String doctorId, Connection con) throws SQLException, ClassNotFoundException;
    boolean isDirector(String doctorId, Connection con) throws SQLException, ClassNotFoundException;
    boolean updateDoctor(String doctorId, String hospitalId, Connection con) throws SQLException, ClassNotFoundException;

    List<Doctor> getDoctorList(String hospitalId, Connection con) throws SQLException;

    boolean saveDotor(DoctorDto doctorDto, Connection con) throws SQLException;

    List<Doctor> getDoctorList(Connection con) throws SQLException;
}

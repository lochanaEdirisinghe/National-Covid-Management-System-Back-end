package com.spark.ncms.repository.custom;


import java.sql.Connection;
import java.sql.SQLException;

public interface DoctorRepository extends SuperRepository{
    String getHospitalId(String doctorId, Connection con) throws SQLException, ClassNotFoundException;
    boolean isDirector(String doctorId, Connection con) throws SQLException, ClassNotFoundException;
    boolean updateDoctor(String doctorId, String hospitalId, Connection con) throws SQLException, ClassNotFoundException;
}

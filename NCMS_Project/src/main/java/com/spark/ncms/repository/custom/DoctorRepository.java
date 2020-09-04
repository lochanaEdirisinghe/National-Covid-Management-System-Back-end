package com.spark.ncms.repository.custom;


import java.sql.Connection;
import java.sql.SQLException;

public interface DoctorRepository {
    String getHospitalId(String doctorId, Connection con) throws SQLException, ClassNotFoundException;
}

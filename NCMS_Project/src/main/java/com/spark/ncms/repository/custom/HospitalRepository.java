package com.spark.ncms.repository.custom;

import com.spark.ncms.entity.Hospital;

import java.sql.Connection;
import java.sql.SQLException;

public interface HospitalRepository {
    boolean addNewHospital(Hospital hospital, Connection con ) throws SQLException, ClassNotFoundException;
    Hospital getHospital(String id , Connection con) throws SQLException, ClassNotFoundException;
}

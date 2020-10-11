package com.spark.ncms.repository.custom;

import com.spark.ncms.entity.Hospital;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface HospitalRepository extends SuperRepository {
    boolean addNewHospital(Hospital hospital, Connection con ) throws SQLException, ClassNotFoundException;
    Hospital getHospital(String id , Connection con) throws SQLException, ClassNotFoundException;
    List<Hospital> getAllHospitals(Connection con) throws SQLException, ClassNotFoundException;
}

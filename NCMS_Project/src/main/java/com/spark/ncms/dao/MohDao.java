package com.spark.ncms.dao;

import com.spark.ncms.dto.HospitalDto;
import com.spark.ncms.entity.Hospital;
import com.spark.ncms.entity.PatientQueue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MohDao {
    List<PatientQueue> getQueuePatients(Connection con) throws SQLException, ClassNotFoundException;
    boolean addNewHospital(Hospital hospital, Connection con ) throws SQLException, ClassNotFoundException;
}

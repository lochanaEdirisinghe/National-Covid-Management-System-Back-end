package com.spark.ncms.dao;

import com.spark.ncms.entity.Hospital;
import com.spark.ncms.entity.Patient;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PatientDao {
    ArrayList<String> BedsAvailableHospitals(Connection con) throws SQLException, ClassNotFoundException;
    boolean savePatient(Patient patient ,Connection con) throws SQLException, ClassNotFoundException;
    Hospital getHospital(String id , Connection con) throws SQLException, ClassNotFoundException;
    boolean patientBedUpdate(String hospitalId, int bedId, String patientId, Connection con) throws SQLException, ClassNotFoundException;
    int getBedId(String hospitalId, Connection con) throws SQLException, ClassNotFoundException;
    boolean addToQueue(String patientId, Connection con) throws SQLException, ClassNotFoundException;
    int getQueueNo(String patientId, Connection con) throws SQLException, ClassNotFoundException;
    Patient getPatient(String patientId, Connection con) throws SQLException, ClassNotFoundException;

}

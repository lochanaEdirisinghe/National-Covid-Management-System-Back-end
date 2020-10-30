package com.spark.ncms.repository.custom;

import com.spark.ncms.entity.HospitalBed;
import com.spark.ncms.entity.PatientBedHospital;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface HospitalBedRepository extends SuperRepository{

   List<String> BedsAvailableHospitals(Connection con) throws SQLException, ClassNotFoundException;
   List<HospitalBed> getHospitalBedList(String hospitalId, Connection con) throws SQLException, ClassNotFoundException;
   int getBedCount(String doctorId, Connection con) throws SQLException, ClassNotFoundException;
   boolean updateHospitalBed(String patientId, Connection con) throws SQLException, ClassNotFoundException;
   int getBedId(String hospitalId, Connection con) throws SQLException, ClassNotFoundException;
   boolean patientBedUpdate(String hospitalId, int bedId, String patientId, Connection con) throws SQLException, ClassNotFoundException;
   boolean addHospitalBed(String hospitalId, Connection con) throws SQLException, ClassNotFoundException;
   PatientBedHospital getPatientBed(String patientId, Connection con) throws SQLException, ClassNotFoundException;

}

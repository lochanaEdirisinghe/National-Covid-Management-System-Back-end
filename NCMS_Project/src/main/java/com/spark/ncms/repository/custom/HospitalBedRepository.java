package com.spark.ncms.repository.custom;

import com.spark.ncms.entity.HospitalBed;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface HospitalBedRepository {

   List<String> BedsAvailableHospitals(Connection con) throws SQLException, ClassNotFoundException;
   List<HospitalBed> getHospitalBedList(String hospitalId, Connection con) throws SQLException, ClassNotFoundException;
   List<HospitalBed> getBedList(Connection con) throws SQLException, ClassNotFoundException;
   boolean updateHospitalBed(String patientId, Connection con) throws SQLException, ClassNotFoundException;
   int getBedId(String hospitalId, Connection con) throws SQLException, ClassNotFoundException;
   boolean patientBedUpdate(String hospitalId, int bedId, String patientId, Connection con) throws SQLException, ClassNotFoundException;
}

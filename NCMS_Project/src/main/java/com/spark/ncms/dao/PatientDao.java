package com.spark.ncms.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import java.util.ArrayList;

public interface PatientDao {
    ArrayList<String> BedsAvailableHospitals(BasicDataSource bds);
}

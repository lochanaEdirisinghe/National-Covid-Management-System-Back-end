package com.spark.ncms.service;

import com.spark.ncms.dto.PatientDto;
import com.spark.ncms.response.PatientResponse;
import org.apache.commons.dbcp2.BasicDataSource;

public interface PatientService {
    PatientResponse savePatient(PatientDto patientDto, BasicDataSource bds);
}

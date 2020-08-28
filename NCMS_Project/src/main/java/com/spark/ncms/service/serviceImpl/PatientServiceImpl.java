package com.spark.ncms.service.serviceImpl;

import com.spark.ncms.dao.PatientDao;
import com.spark.ncms.dao.daoImpl.PatientDaoImpl;
import com.spark.ncms.dto.PatientDto;
import com.spark.ncms.service.PatientService;
import com.spark.ncms.response.PatientResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.ArrayList;

public class PatientServiceImpl implements PatientService {

    private PatientDao patientDao = new PatientDaoImpl();

    @Override
    public PatientResponse savePatient(PatientDto patientDto, BasicDataSource bds){
        int locationX = patientDto.getLocationX();
        int locationY = patientDto.getLocationY();

        ArrayList<String> hospitals = patientDao.BedsAvailableHospitals(bds);
        

    }
}

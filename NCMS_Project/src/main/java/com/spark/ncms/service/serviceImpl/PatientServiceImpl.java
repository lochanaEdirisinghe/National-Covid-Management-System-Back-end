package com.spark.ncms.service.serviceImpl;

import com.spark.ncms.dao.PatientDao;
import com.spark.ncms.dao.daoImpl.PatientDaoImpl;
import com.spark.ncms.dto.PatientDto;
import com.spark.ncms.entity.Hospital;
import com.spark.ncms.entity.Patient;
import com.spark.ncms.service.PatientService;
import com.spark.ncms.response.PatientResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class PatientServiceImpl implements PatientService {

    private PatientDao patientDao = new PatientDaoImpl();

    @Override
    public PatientResponse savePatient(PatientDto patientDto, Connection con) throws SQLException, ClassNotFoundException {

        String patientId = idGenerator(patientDto.getFirstName());
        Patient patientEntity = new Patient(patientId, patientDto.getFirstName(), patientDto.getLastName(), patientDto.getDistrict(),
                patientDto.getLocationX(), patientDto.getLocationY(), null, patientDto.getGender(), patientDto.getContactNo(),
                patientDto.getEmail(), patientDto.getAge(), null, null, null, null);

        boolean isPatientAdded = patientDao.savePatient(patientEntity, con);

        int locationX = patientDto.getLocationX();
        int locationY = patientDto.getLocationY();
        double minimumDistance=0.0;
        String finalHid= "";
        String finalHname="";
        int queueNo=0;
        ArrayList<Hospital> hospitalDetails = new ArrayList<>();

        ArrayList<String> hospitals = patientDao.BedsAvailableHospitals(con);
        if(!hospitals.isEmpty()){
            for (String Hid:hospitals) {
                hospitalDetails.add(patientDao.getHospital(Hid, con));
            }
            for (Hospital hospital:hospitalDetails) {
                System.out.println(hospital.getName());
                double distance = Math.sqrt((hospital.getLocation_x() - locationX) * (hospital.getLocation_x() - locationX) +
                        (hospital.getLocation_y() - locationY) * (hospital.getLocation_y() - locationY));
                System.out.println(distance);
                if(minimumDistance==0.0){
                    minimumDistance=distance;
                    finalHid = hospital.getId();
                    finalHname = hospital.getName();
                }else{
                    if (minimumDistance>distance ){
                        minimumDistance=distance ;
                        finalHid = hospital.getId();
                        finalHname = hospital.getName();
                    }
                }
                System.out.println(minimumDistance);

            }

            int bedId = patientDao.getBedId(finalHid, con);
            boolean isBedUpdated = patientDao.patientBedUpdate(finalHid, bedId, patientId, con);

            return new PatientResponse(patientId, bedId,finalHname, 0);

        }
        boolean addedToQueue = patientDao.addToQueue(patientId, con);
        if (addedToQueue){
            queueNo = patientDao.getQueueNo(patientId, con);
        }

        return new PatientResponse(patientId, 0,finalHname, queueNo);

    }


    public String idGenerator(String firstName){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String randomstring="123456789";
        for (int i = 0; i < 3; i++) {
            sb.append(randomstring.charAt(random.nextInt(randomstring.length())));
        }
        String randomId = "Pid_"+sb.toString()+firstName.substring(0,3);
        return randomId;
    }
}

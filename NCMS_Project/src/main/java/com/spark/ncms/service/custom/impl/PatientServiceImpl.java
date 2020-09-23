package com.spark.ncms.service.custom.impl;

import com.spark.ncms.repository.RepoFactory;
import com.spark.ncms.repository.RepoType;
import com.spark.ncms.repository.custom.*;
import com.spark.ncms.dto.PatientDto;
import com.spark.ncms.entity.Hospital;
import com.spark.ncms.entity.Patient;
import com.spark.ncms.service.custom.PatientService;
import com.spark.ncms.response.PatientResponse;
import com.spark.ncms.service.custom.SuperSevice;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PatientServiceImpl implements PatientService {

    private HospitalRepository hospitalRepo;
    private QueueRepository queueRepo;
    private HospitalBedRepository hospitalBedRepo;
    private PatientRepository patientRepo;
    private RepoFactory repoFactory;

    public PatientServiceImpl() {
        this.repoFactory=RepoFactory.getInstance();
        hospitalRepo = repoFactory.getRepo(RepoType.HOSPITAL);
        queueRepo = repoFactory.getRepo(RepoType.PATIENT_QUEUE);
        hospitalBedRepo = repoFactory.getRepo(RepoType.HOSPITAL_BED);
        patientRepo = repoFactory.getRepo(RepoType.PATIENT);
    }


    @Override
    public PatientResponse savePatient(PatientDto patientDto, Connection con) throws SQLException, ClassNotFoundException {

        String patientId = idGenerator(patientDto.getFirstName());
        Patient patientEntity = new Patient(patientId, patientDto.getFirstName(), patientDto.getLastName(), patientDto.getDistrict(),
                patientDto.getLocationX(), patientDto.getLocationY(), null, patientDto.getGender(), patientDto.getContactNo(),
                patientDto.getEmail(), patientDto.getAge(), null, null, null, null);

        boolean isPatientAdded = patientRepo.savePatient(patientEntity, con);

        int patientX = patientDto.getLocationX();
        int patientY = patientDto.getLocationY();
        double minimumDistance = 0.0;
        String finalHid = "";
        String finalHname = "";
        int queueNo = 0;
        ArrayList<Hospital> hospitalDetails = new ArrayList<>();

        List<String> hospitals = hospitalBedRepo.BedsAvailableHospitals(con);
        if (!hospitals.isEmpty()) {
            for (String Hid : hospitals) {
                hospitalDetails.add(hospitalRepo.getHospital(Hid, con));
            }
            for (Hospital hospital : hospitalDetails) {
                System.out.println(hospital.getName());

                double distance = findDistance(hospital.getLocation_x(), hospital.getLocation_y(), patientX, patientY);

                System.out.println(distance);
                if (minimumDistance == 0.0) {
                    minimumDistance = distance;
                    finalHid = hospital.getId();
                    finalHname = hospital.getName();
                } else {
                    if (minimumDistance > distance) {
                        minimumDistance = distance;
                        finalHid = hospital.getId();
                        finalHname = hospital.getName();
                    }
                }
                System.out.println(minimumDistance);

            }

            int bedId = hospitalBedRepo.getBedId(finalHid, con);
            boolean isBedUpdated = hospitalBedRepo.patientBedUpdate(finalHid, bedId, patientId, con);

            return new PatientResponse(patientId, bedId, finalHname, 0);

        }
        boolean addedToQueue = queueRepo.addToQueue(patientId, con);
        if (addedToQueue) {
            queueNo = queueRepo.getQueueNo(patientId, con);
        }

        return new PatientResponse(patientId, 0, finalHname, queueNo);

    }

    @Override
    public PatientDto getPatient(String patientId, Connection con) throws SQLException, ClassNotFoundException {
        Patient patient = patientRepo.getPatient(patientId, con);
        return new PatientDto(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getDistrict(), patient.getLocationX(),
                patient.getLocationY(), patient.getSeverity_level(), patient.getGender(), patient.getContact(), patient.getEmail(), patient.getAge(),
                patient.getAdmit_date(), patient.getAdmitted_by(), patient.getDischarge_date(), patient.getDischarged_by());
    }

    @Override
    public List<PatientDto> getAllPatient(Connection con) throws SQLException, ClassNotFoundException {
        List<PatientDto> allpatients = new ArrayList<>();
        List<Patient> allPatient = patientRepo.getAllPatient(con);
        for (Patient patient : allPatient) {
            allpatients.add(new PatientDto(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getDistrict(), patient.getLocationX(),
                    patient.getLocationY(), patient.getSeverity_level(), patient.getGender(), patient.getContact(), patient.getEmail(), patient.getAge(),
                    patient.getAdmit_date(), patient.getAdmitted_by(), patient.getDischarge_date(), patient.getDischarged_by()));
        }
        return allpatients;
    }

    @Override
    public boolean updatePatient(String patientId, String doctorId, String doctorRole, Connection con) throws SQLException, ClassNotFoundException {
        if (doctorRole.equals("admit")) {
            boolean isUpdated = patientRepo.updatePatient(patientId, doctorId, doctorRole, con);
            return isUpdated;
        } else if (doctorRole.equals("discharge")) {
            boolean isUpdatedPatient = patientRepo.updatePatient(patientId, doctorId, doctorRole, con);
            boolean isUpdatedHosBed = hospitalBedRepo.updateHospitalBed(patientId, con);
            if (isUpdatedPatient == true && isUpdatedHosBed == true) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public String idGenerator(String firstName) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String randomstring = "123456789";
        for (int i = 0; i < 3; i++) {
            sb.append(randomstring.charAt(random.nextInt(randomstring.length())));
        }
        String randomId = "Pid_" + sb.toString() + firstName.substring(0, 3);
        return randomId;
    }

    public double findDistance(int hospitalX, int hospitalY, int patientX, int patientY){
        return Math.sqrt((hospitalX - patientX) * (hospitalX - patientX) +
                (hospitalY - patientY) * (hospitalY - patientY));
    }


}

package com.spark.ncms.service.custom.impl;

import com.spark.ncms.dto.HospitalCount;
import com.spark.ncms.dto.PatientCount;
import com.spark.ncms.entity.*;
import com.spark.ncms.repository.RepoFactory;
import com.spark.ncms.repository.RepoType;
import com.spark.ncms.repository.custom.*;
import com.spark.ncms.dto.PatientDto;
import com.spark.ncms.response.HospitaBedResponse;
import com.spark.ncms.response.HospitalBedRespDto;
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

        int patientX = patientDto.getLocationX();
        int patientY = patientDto.getLocationY();
        double minimumDistance = 0.0;
        String finalHid = "";
        String finalHname = "";
        int queueNo = 0;
        ArrayList<Hospital> hospitalDetails = new ArrayList<>();
        List<String> hospitals = hospitalBedRepo.BedsAvailableHospitals(con);

        try {
            con.setAutoCommit(false);

            if (patientX <= 600 && patientY <= 600) {
                String patientId = idGenerator(patientDto.getFirstName());
                Patient patientEntity = new Patient(patientId, patientDto.getFirstName(), patientDto.getLastName(), patientDto.getDistrict(),
                        patientDto.getLocationX(), patientDto.getLocationY(), null, patientDto.getGender(), patientDto.getContactNo(),
                        patientDto.getEmail(), patientDto.getAge(), null, null, null, null);

                patientRepo.savePatient(patientEntity, con);

                if (!hospitals.isEmpty()) {
                    for (String Hid : hospitals) {
                        hospitalDetails.add(hospitalRepo.getHospital(Hid, con));
                    }
                    for (Hospital hospital : hospitalDetails) {

                        double distance = findDistance(hospital.getLocation_x(), hospital.getLocation_y(), patientX, patientY);

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
                    }

                    int bedId = hospitalBedRepo.getBedId(finalHid, con);
                    hospitalBedRepo.patientBedUpdate(finalHid, bedId, patientId, con);
                    con.commit();
                    return new PatientResponse(patientId, bedId, finalHname, 0);

                }
                con.rollback();
                boolean addedToQueue = queueRepo.addToQueue(patientId, con);
                if (addedToQueue) {
                    con.commit();
                    queueNo = queueRepo.getQueueNo(patientId, con);
                    return new PatientResponse(patientId, 0, finalHname, queueNo);
                }
                con.rollback();

            }
        } finally {
            con.setAutoCommit(true);
        }
        return null;

    }

    @Override
    public PatientDto getPatient(String patientId, Connection con) throws SQLException, ClassNotFoundException {
        Patient patient = patientRepo.getPatient(patientId, con);
        PatientBedHospital patientBed = hospitalBedRepo.getPatientBed(patientId, con);
        List<PatientQueue> queuePatients = queueRepo.getQueuePatients(con);
        for (PatientQueue q: queuePatients){
            if(q.getPatientId().equals(patient.getId())){
                return new PatientDto(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getDistrict(), patient.getLocationX(),
                        patient.getLocationY(), patient.getSeverity_level(), patient.getGender(), patient.getContact(), patient.getEmail(), patient.getAge(),
                        null, null, null, null, 0, null, q.getQueueId());

            }
        }
        return new PatientDto(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getDistrict(), patient.getLocationX(),
                patient.getLocationY(), patient.getSeverity_level(), patient.getGender(), patient.getContact(), patient.getEmail(), patient.getAge(),
                patient.getAdmit_date(), patient.getAdmitted_by(), patient.getDischarge_date(), patient.getDischarged_by(), patientBed.getBedId(), patientBed.getHId(), 0);
    }

    @Override
    public List<PatientDto> getAllPatient(Connection con) throws SQLException, ClassNotFoundException {

        List<PatientDto> patientList = new ArrayList<>();
        List<HospitalBed> hospitalBedList = hospitalBedRepo.getHospitalBedList(null,con);
        for (HospitalBed hospitalBed: hospitalBedList) {
            Patient patient = patientRepo.getPatient(hospitalBed.getPatientId(), con);
            patientList.add(new PatientDto(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getDistrict(), patient.getLocationX(),
                    patient.getLocationY(), patient.getSeverity_level(), patient.getGender(), patient.getContact(), patient.getEmail(), patient.getAge(),
                    patient.getAdmit_date(), patient.getAdmitted_by(), patient.getDischarge_date(), patient.getDischarged_by(), hospitalBed.getBedId(), hospitalBed.getHospitalId(), 0));
        }

        return patientList;

        }

    @Override
    public boolean updatePatient(String patientId, String doctorId, String slevel, String doctorRole, Connection con) throws SQLException, ClassNotFoundException {
        if (doctorRole.equals("admit")) {
            boolean isUpdated = patientRepo.updatePatient(patientId, doctorId, slevel, doctorRole, con);
            return isUpdated;
        } else if (doctorRole.equals("discharge")) {
            try {
                con.setAutoCommit(false);
                boolean isUpdatedPatient = patientRepo.updatePatient(patientId, doctorId,  slevel, doctorRole, con);
                if(isUpdatedPatient){
                    boolean isUpdatedHosBed = hospitalBedRepo.updateHospitalBed(patientId, con);
                    if (isUpdatedHosBed){
                        con.commit();
                        return true;
                    }
                }
                con.rollback();
            }finally {
                con.setAutoCommit(true);
            }

        }
        return false;
    }

    @Override
    public PatientCount getPatientCount(Connection con) throws SQLException, ClassNotFoundException {
        return patientRepo.getPatientCount(con);

    }

    @Override
    public List<HospitalCount> getHospitalPatientCount(Connection con) throws SQLException, ClassNotFoundException {
        List<HospitalCount>  patientCount = new ArrayList<>();
        List<Hospital> allHospitals = hospitalRepo.getAllHospitals(con);
        for (Hospital h: allHospitals){
            int patients = 10 - (hospitalBedRepo.getBedCount(h.getId(), con));
            patientCount.add(new HospitalCount(h.getId(), h.getName(),h.getDistrict(), patients));
        }
        return patientCount;
    }


    public String idGenerator(String firstName) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String randomstring = "123456789";
        for (int i = 0; i < 3; i++) {
            sb.append(randomstring.charAt(random.nextInt(randomstring.length())));
        }
        String randomId = "Pid" + sb.toString() + firstName.substring(0, 3);
        return randomId;
    }

    public double findDistance(int hospitalX, int hospitalY, int patientX, int patientY){
        return Math.sqrt((hospitalX - patientX) * (hospitalX - patientX) +
                (hospitalY - patientY) * (hospitalY - patientY));
    }


}

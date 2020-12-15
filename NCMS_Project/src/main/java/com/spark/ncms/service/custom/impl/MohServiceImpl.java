package com.spark.ncms.service.custom.impl;

import com.spark.ncms.dto.*;
import com.spark.ncms.repository.RepoFactory;
import com.spark.ncms.repository.RepoType;
import com.spark.ncms.repository.custom.*;
import com.spark.ncms.entity.Hospital;
import com.spark.ncms.entity.PatientQueue;
import com.spark.ncms.service.custom.MohService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MohServiceImpl implements MohService {

    QueueRepository queueRepo;
    HospitalRepository hospitalRepo;
    HospitalBedRepository hospitalBedRepository;

    public MohServiceImpl() {
        queueRepo = RepoFactory.getInstance().getRepo(RepoType.PATIENT_QUEUE);
        hospitalRepo = RepoFactory.getInstance().getRepo(RepoType.HOSPITAL);
        hospitalBedRepository = RepoFactory.getInstance().getRepo(RepoType.HOSPITAL_BED);

    }

    @Override
    public List<QueueDto> getQueueDetails(Connection con) throws SQLException, ClassNotFoundException {
        List<QueueDto> queueList = new ArrayList<>();
        List<PatientQueue> queuePatients = queueRepo.getQueuePatients(con);
        for (PatientQueue patientQueue : queuePatients) {
            queueList.add(new QueueDto(patientQueue.getQueueId(), patientQueue.getPatientId()));
        }
        return queueList;
    }

    @Override
    public boolean addNewHospital(HospitalDto hospitalDto, Connection con) throws SQLException, ClassNotFoundException {
        Hospital hospital = new Hospital(hospitalDto.getId(), hospitalDto.getName(), hospitalDto.getDistrict(), hospitalDto.getLocationX(), hospitalDto.getLocationY());
        try {
            con.setAutoCommit(false);
            boolean isAdded = hospitalRepo.addNewHospital(hospital, con);
            if (isAdded){
                boolean addBedList = hospitalBedRepository.addHospitalBed(hospital.getId(), con);
                if (addBedList){
                    con.commit();
                    return true;
                }
            }
            con.rollback();
        }finally {
            con.setAutoCommit(true);
        }
        return false;
    }

    @Override
    public List<HospitalCount> getHopspitalCount(Connection con) throws SQLException, ClassNotFoundException {
        List<HospitalCount> bedCounts = new ArrayList<>();
        List<Hospital> allHospitals = hospitalRepo.getAllHospitals(con);
        for (Hospital h : allHospitals) {
            int bedCount = hospitalBedRepository.getBedCount(h.getId(), con);
            bedCounts.add(new HospitalCount(h.getId(), h.getName(),h.getDistrict(), bedCount));
        }
        return bedCounts;
    }

    @Override
    public boolean updateQueue(String hospitalId, Connection con) throws SQLException, ClassNotFoundException {
        boolean isupdated = false;

        try {
            con.setAutoCommit(false);
            List<PatientQueue> queuePatients = queueRepo.getQueuePatients(con);
            for (PatientQueue p : queuePatients) {
                int bedId = hospitalBedRepository.getBedId(hospitalId, con);
                isupdated = hospitalBedRepository.patientBedUpdate(hospitalId, bedId, p.getPatientId(), con);
            }
            if (isupdated) {
                boolean isdelete = queueRepo.deleteQueue(con);
                if (isdelete) {
                    con.commit();
                    return true;
                }
            }
            con.rollback();

        }finally {
            con.setAutoCommit(true);
        }
        return false;
    }

    @Override
    public List<HospitalDto2> getAllHopspitals(Connection con) throws SQLException, ClassNotFoundException {
        List<HospitalDto2> hospitals = new ArrayList<>();
        List<Hospital> allHospitals = hospitalRepo.getAllHospitals(con);
        for (Hospital hospital: allHospitals) {
            int bedCount = hospitalBedRepository.getBedCount(hospital.getId(), con);
            hospitals.add(new HospitalDto2(hospital.getId(), hospital.getName(), hospital.getDistrict(),
                    bedCount, hospital.getLocation_x(), hospital.getLocation_y()));
        }

        return hospitals;
    }
}

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
        boolean isAdded = hospitalRepo.addNewHospital(hospital, con);
        hospitalBedRepository.addHospitalBed(hospital.getId(), con);
        return isAdded;
    }

    @Override
    public List<HospitalCount> getBedCount(Connection con) throws SQLException, ClassNotFoundException {
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
        List<PatientQueue> queuePatients = queueRepo.getQueuePatients(con);
        for (PatientQueue p : queuePatients) {
            int bedId = hospitalBedRepository.getBedId(hospitalId, con);
             isupdated= hospitalBedRepository.patientBedUpdate(hospitalId, bedId, p.getPatientId(), con);
            System.out.println(isupdated);
        }

        boolean isdelete = queueRepo.deleteQueue(con);

        if(isdelete==true && isupdated==true){
            return true;
        }
        return false;
    }
}

package com.spark.ncms.service.custom.impl;

import com.spark.ncms.dto.HospitalBedDto;
import com.spark.ncms.entity.HospitalBed;
import com.spark.ncms.repository.RepoFactory;
import com.spark.ncms.repository.RepoType;
import com.spark.ncms.repository.custom.*;
import com.spark.ncms.dto.HospitalDto;
import com.spark.ncms.dto.QueueDto;
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

    public MohServiceImpl(){
        queueRepo = RepoFactory.getInstance().getRepo(RepoType.PATIENT_QUEUE);
        hospitalRepo = RepoFactory.getInstance().getRepo(RepoType.HOSPITAL);
        hospitalBedRepository = RepoFactory.getInstance().getRepo(RepoType.HOSPITAL_BED);
    }

    @Override
    public List<QueueDto> getQueueDetails(Connection con)throws SQLException, ClassNotFoundException {
        List<QueueDto>  queueList = new ArrayList<>();
        List<PatientQueue> queuePatients = queueRepo.getQueuePatients(con);
        for (PatientQueue patientQueue: queuePatients) {
            queueList.add(new QueueDto(patientQueue.getQueueId(), patientQueue.getPatientId()));
        }
        return queueList;
    }

    @Override
    public boolean addNewHospital(HospitalDto hospitalDto, Connection con)throws SQLException, ClassNotFoundException {
        Hospital hospital = new Hospital(hospitalDto.getId(), hospitalDto.getName(), hospitalDto.getDistrict(), hospitalDto.getLocationX(), hospitalDto.getLocationY());
        boolean isAdded = hospitalRepo.addNewHospital(hospital, con);
        return isAdded;
    }

    @Override
    public List<HospitalBedDto> getBedDetails(Connection con) throws SQLException, ClassNotFoundException {
        List<HospitalBed> bedList = hospitalBedRepository.getBedList(con);
        List<HospitalBedDto> hospitalBedDtoList = new ArrayList<>();
        for (HospitalBed hospitalBed: bedList ) {
            hospitalBedDtoList.add(new HospitalBedDto(hospitalBed.getBedId(), hospitalBed.getHospitalId(), hospitalBed.getPatientId()));
        }
        return hospitalBedDtoList;
    }
}

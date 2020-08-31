package com.spark.ncms.service.serviceImpl;

import com.spark.ncms.dao.MohDao;
import com.spark.ncms.dao.daoImpl.MohDaoImpl;
import com.spark.ncms.dto.HospitalDto;
import com.spark.ncms.dto.QueueDto;
import com.spark.ncms.entity.Hospital;
import com.spark.ncms.entity.PatientQueue;
import com.spark.ncms.service.MohService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MohServiceImpl implements MohService {

    MohDao mohDao = new MohDaoImpl();

    @Override
    public List<QueueDto> getQueuePatients(Connection con)throws SQLException, ClassNotFoundException {
        List<QueueDto>  queueList = new ArrayList<>();
        List<PatientQueue> queuePatients = mohDao.getQueuePatients(con);
        for (PatientQueue patientQueue: queuePatients) {
            queueList.add(new QueueDto(patientQueue.getQueueId(), patientQueue.getPatientId()));
        }
        return queueList;
    }

    @Override
    public boolean addNewHospital(HospitalDto hospitalDto, Connection con)throws SQLException, ClassNotFoundException {
        Hospital hospital = new Hospital(hospitalDto.getId(), hospitalDto.getName(), hospitalDto.getDistrict(), hospitalDto.getLocationX(), hospitalDto.getLocationY());
        boolean isAdded = mohDao.addNewHospital(hospital, con);
        return isAdded;
    }
}

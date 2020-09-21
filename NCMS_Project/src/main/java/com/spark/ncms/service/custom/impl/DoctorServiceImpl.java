package com.spark.ncms.service.custom.impl;

import com.spark.ncms.entity.Patient;
import com.spark.ncms.repository.RepoFactory;
import com.spark.ncms.repository.RepoType;
import com.spark.ncms.repository.custom.DoctorRepository;
import com.spark.ncms.repository.custom.HospitalBedRepository;
import com.spark.ncms.repository.custom.PatientRepository;
import com.spark.ncms.entity.HospitalBed;
import com.spark.ncms.response.HospitaBedResponse;
import com.spark.ncms.response.HospitalBedRespDto;
import com.spark.ncms.service.custom.DoctorService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorServiceImpl implements DoctorService {

    private DoctorRepository doctorRepo;
    private HospitalBedRepository hospitalBedRepo;
    private PatientRepository patientRepo;

    public DoctorServiceImpl(){
        doctorRepo = RepoFactory.getInstance().getRepo(RepoType.DOCTOR);
        hospitalBedRepo = RepoFactory.getInstance().getRepo(RepoType.HOSPITAL_BED);
        patientRepo = RepoFactory.getInstance().getRepo(RepoType.PATIENT);
    }

    @Override
    public HospitaBedResponse getHospitalBedList(String doctorId, Connection con) throws SQLException, ClassNotFoundException{
        List<HospitalBedRespDto> bedList = new ArrayList<>();
        String hospitalId = doctorRepo.getHospitalId(doctorId, con);
        System.out.println(hospitalId);
        List<HospitalBed> hospitalBedList = hospitalBedRepo.getHospitalBedList(hospitalId, con);
        for (HospitalBed hospitalBed: hospitalBedList) {
            Patient patient = patientRepo.getPatient(hospitalBed.getPatientId(), con);
            bedList.add(new HospitalBedRespDto(hospitalBed.getBedId(), hospitalBed.getPatientId(), patient.getAdmitted_by()!=null, patient.getDischarged_by()!=null));
        }
        if(!bedList.isEmpty()) {
            return new HospitaBedResponse(hospitalId, bedList);
        }
        return new HospitaBedResponse(hospitalId, null);
    }


}

package com.spark.ncms.service.custom.impl;

import com.spark.ncms.dto.DoctorDto1;
import com.spark.ncms.dto.DoctorDto2;
import com.spark.ncms.entity.Doctor;
import com.spark.ncms.entity.Patient;
import com.spark.ncms.repository.RepoFactory;
import com.spark.ncms.repository.RepoType;
import com.spark.ncms.repository.custom.DoctorRepository;
import com.spark.ncms.repository.custom.HospitalBedRepository;
import com.spark.ncms.repository.custom.HospitalRepository;
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
    private HospitalRepository hospitalRepo;

    public DoctorServiceImpl(){
        doctorRepo = RepoFactory.getInstance().getRepo(RepoType.DOCTOR);
        hospitalBedRepo = RepoFactory.getInstance().getRepo(RepoType.HOSPITAL_BED);
        patientRepo = RepoFactory.getInstance().getRepo(RepoType.PATIENT);
        hospitalRepo = RepoFactory.getInstance().getRepo(RepoType.HOSPITAL);
    }

    @Override
    public HospitaBedResponse getHospitalBedList(String doctorId, Connection con) throws SQLException, ClassNotFoundException{
        List<HospitalBedRespDto> bedList = new ArrayList<>();
        String hospitalId = doctorRepo.getHospitalId(doctorId, con);
        String hospitalName = hospitalRepo.getHospital(hospitalId, con).getName();
        List<HospitalBed> hospitalBedList = hospitalBedRepo.getHospitalBedList(hospitalId, con);
        for (HospitalBed hospitalBed: hospitalBedList) {
            Patient patient = patientRepo.getPatient(hospitalBed.getPatientId(), con);
            bedList.add(new HospitalBedRespDto(hospitalBed.getBedId(), hospitalBed.getPatientId(), patient.getAdmitted_by()!=null, patient.getDischarged_by()!=null));
        }
        if(!bedList.isEmpty()) {
            return new HospitaBedResponse(hospitalId, hospitalName, bedList);
        }
        return new HospitaBedResponse(hospitalId, hospitalName, null);
    }

    @Override
    public boolean isDirector(String doctorId, Connection con) throws SQLException, ClassNotFoundException {
        boolean isdirector = doctorRepo.isDirector(doctorId, con);
        return isdirector;

    }

    @Override
    public boolean updateDoctor(String doctorId, String hospitalId, Connection con) throws SQLException, ClassNotFoundException {
        boolean isUpdated = doctorRepo.updateDoctor(doctorId, hospitalId, con);
        return isUpdated;
    }

    @Override
    public List<DoctorDto1> getAllDoctors(String hospitalId, Connection con) throws SQLException {
        List<DoctorDto1> doctorDto1s =new ArrayList<>();
        List<Doctor> doctorList = doctorRepo.getDoctorList(hospitalId, con);
        for (Doctor doctor: doctorList) {

            doctorDto1s.add(new DoctorDto1(doctor.getId(), doctor.getName(), doctor.getHospital_id(),
                    doctor.is_director(),doctor.getContactNo()));
        }
        return doctorDto1s;
    }

    @Override
    public boolean saveDoctor(DoctorDto1 doctorDto1, Connection con) throws SQLException {
        return doctorRepo.saveDotor(doctorDto1, con);
    }

    @Override
    public List<DoctorDto2> getAllDoctors(Connection con) throws SQLException {
        List<DoctorDto2> doctorDtos2=new ArrayList<>();
        List<Doctor> doctorList = doctorRepo.getDoctorList(con);
        for (Doctor doctor: doctorList) {
                if(doctor.is_director()==true){
                    doctorDtos2.add(new DoctorDto2(doctor.getId(), doctor.getName(), doctor.getHospital_id(),
                            "Yes",doctor.getContactNo()));
                }else {
                    doctorDtos2.add(new DoctorDto2(doctor.getId(), doctor.getName(), doctor.getHospital_id(),
                            "No",doctor.getContactNo()));
                }
        }
        return doctorDtos2;
    }

}

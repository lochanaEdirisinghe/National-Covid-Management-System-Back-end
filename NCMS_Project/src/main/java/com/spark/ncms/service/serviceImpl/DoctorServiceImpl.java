package com.spark.ncms.service.serviceImpl;

import com.spark.ncms.dao.DoctorDao;
import com.spark.ncms.dao.daoImpl.DoctorDaoImpl;
import com.spark.ncms.dto.HospitalBedDto;
import com.spark.ncms.dto.PatientDto;
import com.spark.ncms.entity.HospitalBed;
import com.spark.ncms.entity.Patient;
import com.spark.ncms.response.HospitaBedResponse;
import com.spark.ncms.service.DoctorService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorServiceImpl implements DoctorService {

    DoctorDao doctorDao = new DoctorDaoImpl();
    @Override
    public HospitaBedResponse getHospitalBedList(String doctorId, Connection con) throws SQLException, ClassNotFoundException{
        List<HospitalBedDto> bedList = new ArrayList<>();
        String hospitalId = doctorDao.getHospitalId(doctorId, con);
        System.out.println(hospitalId);
        List<HospitalBed> hospitalBedList = doctorDao.getHospitalBedList(hospitalId, con);
        for (HospitalBed hospitalBed: hospitalBedList) {
            bedList.add(new HospitalBedDto(hospitalBed.getBedId(), hospitalBed.getPatientId()));
        }
        if(!bedList.isEmpty()) {
            return new HospitaBedResponse(hospitalId, bedList);
        }
        return new HospitaBedResponse(hospitalId, null);
    }


    @Override
    public boolean updatePatient(String patientId, String doctorId, String doctorRole, Connection con) throws SQLException, ClassNotFoundException{
        if(doctorRole.equals("admit")){
            boolean isUpdated = doctorDao.updatePatient(patientId, doctorId, doctorRole, con);
            return isUpdated;
        }else if(doctorRole.equals("discharge")){
            boolean isUpdatedPatient = doctorDao.updatePatient(patientId, doctorId, doctorRole, con);
            boolean isUpdatedHosBed = doctorDao.updateHospitalBed(patientId, con);
            if(isUpdatedPatient==true &&isUpdatedHosBed==true){
                return true;
            }else {
                return false;
            }

        }
        return false;
    }

}

package com.spark.ncms.dao.daoImpl;

import com.spark.ncms.dao.DoctorDao;
import com.spark.ncms.entity.HospitalBed;
import com.spark.ncms.entity.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DoctorDaoImpl implements DoctorDao {
    @Override
    public String getHospitalId(String doctorId, Connection con) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = con.prepareStatement("select hospital_id from doctor where id =?");
        pstm.setObject(1, doctorId);
        ResultSet rst = pstm.executeQuery();
        if(rst.next()){
            return rst.getString(1);
        }

        return null;
    }

    @Override
    public List<HospitalBed> getHospitalBedList(String hospitalId, Connection con)throws SQLException, ClassNotFoundException {
        List<HospitalBed> hospital_beds = new ArrayList<>();
        PreparedStatement pstm = con.prepareStatement("select * from hospital_bed where hospital_id=? and patient_id is not null");
        pstm.setObject(1, hospitalId);
        ResultSet rst = pstm.executeQuery();
        if(rst.next()){
            while (rst.next()){
                hospital_beds.add(new HospitalBed(rst.getInt(1), rst.getString(2), rst.getString(3)));
            }
            return hospital_beds;
        }
        return null;
    }



    @Override
    public boolean updatePatient(String patientId, String doctorId, String doctorRole, Connection con) throws SQLException, ClassNotFoundException {
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        if(doctorRole.equals("admit")){
            PreparedStatement pstm = con.prepareStatement("update patient set admitted_by=?, admit_date=? where id=?");
            pstm.setObject(1, doctorId );
            pstm.setObject(2, date);
            pstm.setObject(3, patientId);
            int i = pstm.executeUpdate();
            if(i>0){
                return true;
            }
            return false;
        }else if(doctorRole.equals("discharge")){
            PreparedStatement pstm = con.prepareStatement("update patient set discharged_by=?, discharge_date=? where id=?");
            pstm.setObject(1, doctorId );
            pstm.setObject(2, date);
            pstm.setObject(3, patientId);
            int i = pstm.executeUpdate();
            if(i>0){
                return true;
            }
            return false;
        }
        return false;

    }

    @Override
    public boolean updateHospitalBed(String patientId, Connection con)throws SQLException, ClassNotFoundException  {
        PreparedStatement pstm = con.prepareStatement("update hospital_bed set patient_id=? where patient_id=?");
        pstm.setObject(1, null);
        pstm.setObject(2, patientId);
        int i = pstm.executeUpdate();
        if(i>0){
            return true;
        }
        return false;
    }

}

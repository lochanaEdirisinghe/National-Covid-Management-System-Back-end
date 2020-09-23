package com.spark.ncms.test;

import com.spark.ncms.service.custom.impl.DoctorServiceImpl;
import com.spark.ncms.service.custom.impl.PatientServiceImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;

public class DoctorServiceImplTest {

    DoctorServiceImpl doctorService;

    DoctorServiceImplTest(){
        this.doctorService=new DoctorServiceImpl();
    }

    @Test
    public void testMocking() throws SQLException, ClassNotFoundException {
        /*Connection connection = mock(Connection.class);
        Assert.assertNotNull(doctorService.getHospitalBedList("D001", connection ));*/
    }
}

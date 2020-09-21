package com.spark.ncms.test;

import com.spark.ncms.service.custom.impl.PatientServiceImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PatientServiceImplTest {

    PatientServiceImpl patientService;

    PatientServiceImplTest(){
        this.patientService=new PatientServiceImpl();
    }

    @Test(dependsOnMethods = "testTimeOut")
    public void testPatientId(){
        Assert.assertNotNull(patientService.idGenerator("lochana"));
    }

    @Test(timeOut = 100)
    public void testTimeOut(){
        patientService.idGenerator("lochana");
    }

    @Test
    public void testDistance(){
        Assert.assertEquals(patientService.findDistance(100, 200, 120,300), 101.9803902718557);
    }
}

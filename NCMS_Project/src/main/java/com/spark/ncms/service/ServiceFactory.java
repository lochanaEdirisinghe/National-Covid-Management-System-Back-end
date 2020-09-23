package com.spark.ncms.service;

import com.spark.ncms.service.custom.impl.AuthServiceImpl;
import com.spark.ncms.service.custom.impl.DoctorServiceImpl;
import com.spark.ncms.service.custom.impl.MohServiceImpl;
import com.spark.ncms.service.custom.impl.PatientServiceImpl;

public class ServiceFactory {
    public static ServiceFactory serviceFactory;

    private ServiceFactory(){

    }
    //Singleton
    public static ServiceFactory getInstance() {
        if(serviceFactory==null){
            serviceFactory=new ServiceFactory();
        }
        return serviceFactory;
    }

    //Generic Method
    public <T> T getService(ServiceType type){
        switch (type){
            case PATIENT:
                return (T)new PatientServiceImpl();
            case DOCTOR:
                return (T)new DoctorServiceImpl();
            case MOH:
                return (T)new MohServiceImpl();
            case USER:
                return (T)new AuthServiceImpl();
                default:
                    return null;
        }
    }

}

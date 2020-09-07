package com.spark.ncms.repository;

import com.spark.ncms.repository.custom.impl.*;

public class RepoFactory {
    private static RepoFactory daoFactory;

    private RepoFactory(){

    }
    public static RepoFactory getInstance(){
        if (null==daoFactory){
            daoFactory= new RepoFactory();
        }
        return daoFactory;
    }

    public <T> T getRepo(RepoType type){
        switch (type){
            case PATIENT:
                return (T)new PatientRepoImpl();
            case HOSPITAL:
                return (T)new HospitalRepoImpl();
            case DOCTOR:
                return (T)new DoctorRepoImpl();
            case HOSPITAL_BED:
                return (T)new HospitalBedRepoImpl();
            case PATIENT_QUEUE:
                return (T)new QueueRepoImpl();
            case USER:
                return (T)new AuthRepoImpl();
            default:
                return null;
        }
    }
}

package com.spark.ncms.repository.custom;

import com.spark.ncms.entity.PatientQueue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface QueueRepository extends SuperRepository {
    boolean addToQueue(String patientId, Connection con) throws SQLException, ClassNotFoundException;
    int getQueueNo(String patientId, Connection con) throws SQLException, ClassNotFoundException;
    List<PatientQueue> getQueuePatients(Connection con) throws SQLException, ClassNotFoundException;
}

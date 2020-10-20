package com.spark.ncms.service.custom;

import com.spark.ncms.dto.HospitalCount;
import com.spark.ncms.dto.HospitalDto;
import com.spark.ncms.dto.QueueDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MohService extends SuperSevice {
    List<QueueDto> getQueueDetails(Connection con) throws SQLException, ClassNotFoundException;
    boolean addNewHospital(HospitalDto hospitalDto, Connection con) throws SQLException, ClassNotFoundException;
    List<HospitalCount> getBedCount(Connection con) throws SQLException, ClassNotFoundException;
    boolean updateQueue(String hospitalId, Connection con) throws SQLException, ClassNotFoundException;
}

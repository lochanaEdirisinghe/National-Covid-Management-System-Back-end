package com.spark.ncms.repository.custom;

import com.spark.ncms.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface AuthRepository extends SuperRepository{
    User userCheck(String userid , String passWord, Connection con) throws SQLException;
}

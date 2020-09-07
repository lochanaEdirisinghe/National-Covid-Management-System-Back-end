package com.spark.ncms.repository.custom.impl;

import com.spark.ncms.entity.User;
import com.spark.ncms.repository.custom.AuthRepository;
import com.spark.ncms.service.custom.AuthService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthRepoImpl implements AuthRepository {

    @Override
    public User userCheck(String username, String password, Connection con) throws SQLException {
        PreparedStatement pstm = con.prepareStatement("select * from user where username=? and password=?");
        pstm.setObject(1, username);
        pstm.setObject(2, password);
        ResultSet rst = pstm.executeQuery();
        if(rst.next()){
            return new User(rst.getString(1), rst.getString(2),rst.getString(3),
                    rst.getBoolean(4), rst.getBoolean(5));
        }
        return null;
    }
}

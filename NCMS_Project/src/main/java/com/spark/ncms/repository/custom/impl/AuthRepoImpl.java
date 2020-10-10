package com.spark.ncms.repository.custom.impl;

import com.spark.ncms.entity.User;
import com.spark.ncms.repository.custom.AuthRepository;
import com.spark.ncms.service.custom.AuthService;

import java.sql.*;

public class AuthRepoImpl implements AuthRepository {

    @Override
    public User userCheck(String userid, String passWord, Connection con) throws SQLException {
        PreparedStatement pstm = con.prepareStatement("select * from user where userId=? and password=?");
        pstm.setObject(1, userid);
        pstm.setObject(2, passWord);
        ResultSet rst = pstm.executeQuery();
        if(rst.next()){
            return new User(rst.getString(1), rst.getString(2),
                    rst.getBoolean(3), rst.getBoolean(4));

        }
        return null;
    }
}

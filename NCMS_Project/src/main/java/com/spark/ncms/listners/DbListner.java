package com.spark.ncms.listners;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;

@WebListener
public class DbListner implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/ncms");
        /*bds.setUrl("jdbc:mysql://ncms.cwzsslvpi7qv.us-east-1.rds.amazonaws.com:3306/ncms");*/
        bds.setUsername("root");
        /*bds.setPassword("lochanapc12");*/
        bds.setPassword("ijse");
        bds.setMaxTotal(6);
        bds.setInitialSize(6);
        sce.getServletContext().setAttribute("db", bds);

       ArrayList<String> userRoles = new ArrayList<>();
       userRoles.add("hospital");
       userRoles.add("moh");

       sce.getServletContext().setAttribute("roles", userRoles);


    }


}

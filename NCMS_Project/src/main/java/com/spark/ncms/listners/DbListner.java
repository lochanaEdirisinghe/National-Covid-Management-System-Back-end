package com.spark.ncms.listners;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DbListner implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/ncms");
        bds.setUsername("root");
        bds.setPassword("ijse");
        bds.setMaxTotal(2);
        bds.setInitialSize(2);
        sce.getServletContext().setAttribute("db", bds);


       /* PatientDto patientDto = new PatientDto();
        sce.getServletContext().setAttribute("patientDto", patientDto);*/
    }

}

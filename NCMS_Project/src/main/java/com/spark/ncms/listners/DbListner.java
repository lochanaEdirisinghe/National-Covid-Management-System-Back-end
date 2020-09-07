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
        bds.setUsername("root");
        bds.setPassword("ijse");
        bds.setMaxTotal(6);
        bds.setInitialSize(6);
        sce.getServletContext().setAttribute("db", bds);



       /* UserDto u1= new UserDto("admin@gmail.com","admin","admin");
        UserDto u2= new UserDto("user@gmail.com","user","user");

        ArrayList<UserDto> allUsers= new ArrayList<>();
        allUsers.add(u1);
        allUsers.add(u2);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("user",allUsers);*/

       ArrayList<String> userRoles = new ArrayList<>();
       userRoles.add("hospital");
       userRoles.add("moh");

        sce.getServletContext().setAttribute("roles", userRoles);


    }


}

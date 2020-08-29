package com.spark.ncms.listners;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.DriverManager;
import java.sql.SQLException;

public class XBasicDataSource extends BasicDataSource {
    @Override
    public synchronized void close() throws SQLException {

        DriverManager.deregisterDriver(DriverManager.getDriver(getUrl()));
        AbandonedConnectionCleanupThread.checkedShutdown();
        super.close();
    }
}

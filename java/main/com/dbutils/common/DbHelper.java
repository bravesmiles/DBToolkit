package com.dbutils.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.model.DBUser;

/**
 * Singleton Pattern
 * @author shihe
 *
 */
public class DbHelper {
	private static Log log = LogFactory.getLog(DbHelper.class);
	
	private static DataSource dataSource;

	private DbHelper() {
	}
	
	public static QueryRunner getQueryRunner(DBUser user) throws SQLException {
		boolean connected = checkIfConnected(user);

		if(connected)
		{
			if(DbHelper.dataSource != null) {	//judge the connection now is the same as the former one.
				BasicDataSource tmpDataSource = (BasicDataSource) DbHelper.dataSource;
				String toJudge = tmpDataSource.getUrl().substring(0,tmpDataSource.getUrl().lastIndexOf("/"));

				String connectionString = getConnectionString(user);
				if (!(connectionString.equals(toJudge)))
					initDataSource(user);
			}
			else {
				initDataSource(user);
			}
			return new QueryRunner(DbHelper.dataSource);
		}
		return new QueryRunner(DbHelper.dataSource);
	}

	public static boolean checkIfConnected(DBUser user) throws SQLException
	{
		String connectionString = getConnectionString(user);
		Connection con = DriverManager.getConnection(connectionString, user.getUsername(), user.getPassword());
		log.info(new Date().toString() + " 数据库连接成功!"); 
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	//initial the dataSource
	private static void initDataSource(DBUser user){
		String driver = getDriver(user);
		String conStr = getConnectionString(user);
		// 配置dbcp数据源
		BasicDataSource dbcpDataSource = new BasicDataSource();
		dbcpDataSource.setUrl(conStr);
		dbcpDataSource.setDriverClassName(driver);
		dbcpDataSource.setUsername(user.getUsername());
		dbcpDataSource.setPassword(user.getPassword());

		dbcpDataSource.setDefaultAutoCommit(true);
		dbcpDataSource.setMaxActive(100);
		dbcpDataSource.setMaxIdle(2);
		dbcpDataSource.setMaxWait(500);
		DbHelper.dataSource = (DataSource) dbcpDataSource;
		log.info(new Date().toString() + " DataSource:["+DbHelper.dataSource+"]初始化成功！");
	}
	//get Database Connection String according to different database type.
	private static String getConnectionString(DBUser user) {
		String connection = null;
		switch (user.getDatabase()) {// jdbc:oracle:thin:scott/tiger@//myhost:1521/myservicename
			case CONF.ORACLE: { 
				connection = CONF.ORACLE_CONNECTION_URL;
				connection = connection.concat(user.getUsername() + ":"
						   + user.getPassword() + "@//" + user.getHost() + ":"
						   + user.getPort() + "/" + user.getDbname());
				break;
			}
			case CONF.MYSQL: { // "jdbc:mysql://   HOST:PORT/SERVICE";
				connection = CONF.MYSQL_CONNECTION_URL;
				connection = connection.concat(user.getHost() + ":"
						   + user.getPort() + "/" + user.getDbname());
				break;
			}
			case CONF.SQLSERVER: { // "jdbc:sqlserver://   HOST:PORT";
				connection = CONF.SQLSERVER_CONNECTION_URL;
				connection = connection.concat(user.getHost() + ":"
						   + user.getPort() + ";user=" + user.getUsername()
						   + ";password=" + user.getPassword()
						   + ";DatabaseName=" + user.getDbname());
				break;
			}
		}
		return connection;
	}
	//get Database Driver according to different database type.
	private static String getDriver(DBUser user) {
		String driver = null;
		switch (user.getDatabase()) {
			case CONF.ORACLE:	 {	driver = CONF.ORACLE_DRIVER;	break;	}
			case CONF.MYSQL: 	 {	driver = CONF.MYSQL_DRIVER;		break;	}
			case CONF.SQLSERVER: {	driver = CONF.SQLSERVER_DRIVER;	break;	}
		}
		return driver;
	}
}
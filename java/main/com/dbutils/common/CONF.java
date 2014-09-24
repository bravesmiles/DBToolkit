package com.dbutils.common;

/**
 * All Configuration.
 * @author Sherwin He
 */
public class CONF {
	/**conf file.*/
	public final static String filename = "files/dbusers.xml";
	/**query file.*/
	public final static String queryfile = "files/query.file";

	/**Oracle */
    public final static int ORACLE = 0;
    /**mysql */
    public final static int MYSQL = 1;
    /**SQLServer */
    public final static int SQLSERVER = 2;
	
	//DataBase Driver.
	/**Oracle Driver for JDBC.*/
	public static final String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
	/**MySQL Driver for JDBC.*/
	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	/**SQLServer Driver for JDBC.*/
	public static final String SQLSERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	
	//DataBase Connection String.
	/**Oracle Driver for JDBC Connection.<br><br>For Example:<br>jdbc:oracle:thin:@localhost:1521:orcl */
	public static final String ORACLE_CONNECTION_URL = "jdbc:oracle:thin:";
	/**MySQL Driver for JDBC Connection.<br><br>For Example:<br>jdbc:mysql://localhost:3306/dbname */
	public static final String MYSQL_CONNECTION_URL = "jdbc:mysql://";
	/**SQLServer Driver for JDBC Connection.<br><br>For Example:<br>jdbc:sqlserver://localhost:1433;dbname=AdventureWorks; */
	public static final String SQLSERVER_CONNECTION_URL = "jdbc:sqlserver://";
	
}

package com.model;

/**
 * The attribute for XML file-dbusers.
 * @author shihe
 *
 */
public class DBUser {
	
	private String confname;
	private int database = 1; //0-oracle 1-mysql 2-sqlserver
	private String host = "localhost";
	private int port = 3306;
	private String username = "root";
	private String password = "";
	private String dbname;
	
	public int getDatabase() {
		return database;
	}
	public void setDatabase(int database) {
		this.database = database;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfname() {
		return confname;
	}
	public void setConfname(String confname) {
		this.confname = confname;
	}
	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
}

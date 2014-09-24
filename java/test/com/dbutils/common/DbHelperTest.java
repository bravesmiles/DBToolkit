package com.dbutils.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import com.model.DBUser;

public class DbHelperTest {

	@Test
	public void testSQLServer()	//无法打开登录所请求的数据库 "ProductionEd"。登录失败。
	{
		// Create a variable for the connection string.
	      String connectionUrl = "jdbc:sqlserver://10.224.167.13:1433;" +
	         "databaseName=ProductionEd;user=pivotal;password=pass";

	      // Declare the JDBC objects.
	      Connection con = null;
	      Statement stmt = null;
	      ResultSet rs = null;

	      try {
	         // Establish the connection.
	         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	         con = DriverManager.getConnection(connectionUrl);

	         // Create and execute an SQL statement that returns some data.
	         String SQL = "SELECT TOP 10 * FROM Person.Contact";
	         stmt = con.createStatement();
	         rs = stmt.executeQuery(SQL);

	         // Iterate through the data in the result set and display it.
	         while (rs.next()) {
	            System.out.println(rs.getString(4) + " " + rs.getString(6));
	         }
	      }

	      // Handle any errors that may have occurred.
	      catch (Exception e) {
	         e.printStackTrace();
	      }
	      finally {
	         if (rs != null) try { rs.close(); } catch(Exception e) {}
	         if (stmt != null) try { stmt.close(); } catch(Exception e) {}
	         if (con != null) try { con.close(); } catch(Exception e) {}
	      }
	}
	
	@Test
	public void testCheckIfConnected() throws SQLException {

		// String user = "pivotal";
		// String password = "pass";
		// Connection con =
		// DriverManager.getConnection("jdbc:sqlserver://10.224.167.13:1433;user=pivotal;password=pass;DatabaseName=ProductionEd",
		// user, password);// 连接数据库对象
		// System.out.println("连接数据库成功");
		// con.close();

		DBUser user = new DBUser();
		user.setConfname("test");
		user.setHost("10.224.167.13");
		user.setDatabase(2);
		user.setPassword("pass");
		user.setDbname("ProductionEd");
		user.setPort(1433);
		user.setUsername("pivotal");
		QueryRunner runner = DbHelper.getQueryRunner(user);

		List<Map<String, Object>> list = runner.query("show tables",
				new MapListHandler(), (Object[]) null);

		List<Integer> borderList = new ArrayList<Integer>();
		for (int i = 0; i < list.get(0).size(); i++)
			borderList.add(-1);

		Integer data = 0;
		for (Iterator<Map<String, Object>> li = list.iterator(); li.hasNext();) {
			int i = 0;
			Map<String, Object> m = li.next();
			for (Iterator<Entry<String, Object>> mi = m.entrySet().iterator(); mi
					.hasNext();) {
				Entry<String, Object> e1 = mi.next();
				data = e1.getValue().toString().length() + 3;
				if (data > borderList.get(i))
					borderList.set(i, data);
				i++;
			}
		}
		System.out.println(borderList);

		// printf the header of header.
		for (Integer t : borderList) {
			System.out.print("+");
			for (int i = 0; i < t; i++)
				System.out.print("-");
		}
		System.out.print("+\n");

		// printf the body of hearder.
		for (Iterator<Map<String, Object>> li = list.iterator(); li.hasNext();) {
			int index = 0;
			Map<String, Object> m = li.next();

			int tmpLen = 0;
			Set<String> keySet = m.keySet();
			Object[] keys = keySet.toArray();
			for (int i = 0; i < keys.length - 1; i++) {
				System.out.printf(": %s  ", keys[i]);
				tmpLen = keys[i].toString().length() + 3;
				for (int j = 0; j < borderList.get(index) - tmpLen; j++)
					System.out.print(" ");
				index++;
			}
			System.out.printf(": %s ", keys[keys.length - 1]); // 少一个
			tmpLen = keys[keys.length - 1].toString().length() + 3;
			for (int j = 0; j < borderList.get(index) - tmpLen + 1; j++)
				System.out.print(" ");

			System.out.printf(":");
			break;
		}
		System.out.println();

		// printf the end of hearder.
		for (Integer t : borderList) {
			System.out.print("+");
			for (int i = 0; i < t; i++)
				System.out.print("-");
		}
		System.out.print("+\n");

		// printf the body of result.
		for (Iterator<Map<String, Object>> li = list.iterator(); li.hasNext();) {
			Map<String, Object> m = li.next();
			int index = 0;
			for (Iterator<Entry<String, Object>> mi = m.entrySet().iterator(); mi
					.hasNext();) {
				Entry<String, Object> e1 = mi.next();
				System.out.printf(": %s  ", e1.getValue());
				int tmpLen = e1.getValue().toString().length() + 3;
				for (int i = 0; i < borderList.get(index) - tmpLen; i++)
					System.out.print(" ");
				index++;
			}
			System.out.printf(":\n");
		}

		// printf the end of result.
		for (Integer t : borderList) {
			System.out.print("+");
			for (int i = 0; i < t; i++)
				System.out.print("-");
		}
		System.out.print("+");
	}

	@Test
	public void testGetQueryRunner() throws SQLException {

		DBUser user = new DBUser();
		user.setConfname("test2");
		user.setHost("127.0.0.1");
		user.setDatabase(1);
		user.setPassword("hsw111");
		user.setDbname("test");
		user.setPort(3306);
		user.setUsername("root");
		QueryRunner runner = DbHelper.getQueryRunner(user);

		List<Map<String, Object>> list = runner.query("select * from user",
				new MapListHandler(), (Object[]) null);

		List<Integer> borderList = new ArrayList<Integer>();
		for (int i = 0; i < list.get(0).size(); i++)
			borderList.add(-1);

		Integer data = 0;
		for (Iterator<Map<String, Object>> li = list.iterator(); li.hasNext();) {
			int i = 0;
			Map<String, Object> m = li.next();
			for (Iterator<Entry<String, Object>> mi = m.entrySet().iterator(); mi
					.hasNext();) {
				Entry<String, Object> e1 = mi.next();
				data = e1.getValue().toString().length() + 3;
				if (data > borderList.get(i))
					borderList.set(i, data);
				i++;
			}
		}
		System.out.println(borderList);

		// printf the header of header.
		for (Integer t : borderList) {
			System.out.print("+");
			for (int i = 0; i < t; i++)
				System.out.print("-");
		}
		System.out.print("+\n");

		// printf the body of hearder.
		for (Iterator<Map<String, Object>> li = list.iterator(); li.hasNext();) {
			int index = 0;
			Map<String, Object> m = li.next();

			int tmpLen = 0;
			Set<String> keySet = m.keySet();
			Object[] keys = keySet.toArray();
			for (int i = 0; i < keys.length - 1; i++) {
				System.out.printf(": %s  ", keys[i]);
				tmpLen = keys[i].toString().length() + 3;
				for (int j = 0; j < borderList.get(index) - tmpLen; j++)
					System.out.print(" ");
				index++;
			}
			System.out.printf(": %s ", keys[keys.length - 1]); // 少一个
			tmpLen = keys[keys.length - 1].toString().length() + 3;
			for (int j = 0; j < borderList.get(index) - tmpLen + 1; j++)
				System.out.print(" ");

			System.out.printf(":");
			break;
		}
		System.out.println();

		// printf the end of hearder.
		for (Integer t : borderList) {
			System.out.print("+");
			for (int i = 0; i < t; i++)
				System.out.print("-");
		}
		System.out.print("+\n");

		// printf the body of result.
		for (Iterator<Map<String, Object>> li = list.iterator(); li.hasNext();) {
			Map<String, Object> m = li.next();
			int index = 0;
			for (Iterator<Entry<String, Object>> mi = m.entrySet().iterator(); mi
					.hasNext();) {
				Entry<String, Object> e1 = mi.next();
				System.out.printf(": %s  ", e1.getValue());
				int tmpLen = e1.getValue().toString().length() + 3;
				for (int i = 0; i < borderList.get(index) - tmpLen; i++)
					System.out.print(" ");
				index++;
			}
			System.out.printf(":\n");
		}

		// printf the end of result.
		for (Integer t : borderList) {
			System.out.print("+");
			for (int i = 0; i < t; i++)
				System.out.print("-");
		}
		System.out.print("+");
	}

}

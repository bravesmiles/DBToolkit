package com.listener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import com.dbutils.common.DbHelper;
import com.model.DBUser;

public class BtnUpdateListenerTest {

	@Test
	public void testAdd() throws SQLException
	{
		DBUser user = new DBUser();
		user.setConfname("test2");
		user.setHost("127.0.0.1");
		user.setDatabase(1);
		user.setPassword("hsw111");
		user.setDbname("test");
		user.setPort(3306);
		user.setUsername("root");
		QueryRunner runner = DbHelper.getQueryRunner(user);

		Object[][] params = new Object[][] { {111, "co111", 0, 1 }, {112, "co112", 0, 1 } };  
		int[] result = runner.batch("insert into attr(attr_id,identifier, attrdict_id,attrtype) values(?,?,?,?)", params);
		System.out.println(result.length);
	}
	
	@Test
	public void testDel() throws SQLException
	{
		DBUser user = new DBUser();
		user.setConfname("test2");
		user.setHost("127.0.0.1");
		user.setDatabase(1);
		user.setPassword("hsw111");
		user.setDbname("test");
		user.setPort(3306);
		user.setUsername("root");
		QueryRunner runner = DbHelper.getQueryRunner(user);

		Object[][] params = new Object[][] { {2} };  
		int[] result = runner.batch("delete from attr where attr_id=?", params);
		System.out.println(result.length);
	}
	
	//@Test
	public void testGetQueryRunner() throws SQLException {

		
	}
}

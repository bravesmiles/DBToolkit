package com.helper;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.model.DBUser;
import com.xml.helper.XMLWriter;

public class XMLWriterTest {

	@Test
	public void testSaveToXML() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DBUser user = new DBUser();
		user.setConfname("test");
		user.setHost("10.224.167.13");
		user.setDatabase(2);
		user.setPassword("pass");
		user.setDbname("ProductionEd");
		user.setPort(1433);
		user.setUsername("pivotal");
		
		XMLWriter.SaveToXML( user.getConfname(), user, "C:\\Users\\shihe\\Desktop\\Toolkit_for_DBs\\dbusers.xml");
	}
	
	//@Test
	public void testDeleteFromXML() throws ParserConfigurationException, SAXException, IOException
	{
		XMLWriter.DeleteFromXML("C:\\Users\\shihe\\Desktop\\Toolkit_for_DBs\\dbusers.xml", "2");
	}
	
	//@Test
	public void testRenameFromXML()
	{
		XMLWriter.RenameFromXML("C:\\Users\\shihe\\Desktop\\Toolkit_for_DBs\\dbusers.xml", "1", "2");
	}
	
}

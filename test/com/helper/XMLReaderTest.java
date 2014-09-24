package com.helper;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.model.DBUser;
import com.xml.helper.XMLReader;

public class XMLReaderTest {

	@Test
	public void testRead() {
		List<DBUser> dblist;
		try {
			dblist = XMLReader.read("C:\\Users\\shihe\\Desktop\\Toolkit for DBs\\dbusers.xml");
			for(int i=0; i<dblist.size(); i++)
			{
				DBUser user = dblist.get(i);
				System.out.println("confname:"+user.getConfname()
						+" database:"+user.getDatabase()
						+" host:"+user.getHost()
						+" port:"+user.getPort()
						+" username:"+user.getUsername()
						+" password:"+user.getPassword());
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
	}

}

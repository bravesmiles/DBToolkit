package com.xml.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.model.DBUser;

/**
 * Read operation with XML. 
 * @author shihe
 */
public class XMLReader {
	
	public static List<DBUser> read(String filePath) throws ParserConfigurationException, SAXException, IOException
	{
		List<DBUser> dbList = new ArrayList<DBUser>();
		
		File file = new File(filePath);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		doc.getDocumentElement().normalize();
		NodeList nodeLst = doc.getElementsByTagName("user");

		DBUser dbuser = null;

		for (int s = 0; s < nodeLst.getLength(); s++) {
			dbuser = new DBUser();
			Node fstNode = nodeLst.item(s);
			if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
				Element fstElmnt = (Element) fstNode;
				// get confname
				NodeList confnameNmElmntLst = fstElmnt.getElementsByTagName("confname");
				Element confnameNmElmnt = (Element) confnameNmElmntLst.item(0);
				NodeList confnameNm = confnameNmElmnt.getChildNodes();
				if (confnameNm.item(0) == null)
					dbuser.setConfname("");
				else
					dbuser.setConfname(((Node) confnameNm.item(0)).getNodeValue());

				// get database
				NodeList databaseNmElmntLst = fstElmnt.getElementsByTagName("database");
				Element databaseNmElmnt = (Element) databaseNmElmntLst.item(0);
				NodeList databaseNm = databaseNmElmnt.getChildNodes();
				if (databaseNm.item(0) == null)
					dbuser.setDatabase(-1);
				else
					dbuser.setDatabase(Integer.parseInt((((Node) databaseNm.item(0)).getNodeValue())));

				// get host
				NodeList hostNmElmntLst = fstElmnt.getElementsByTagName("host");
				Element hostNmElmnt = (Element) hostNmElmntLst.item(0);
				NodeList hostNm = hostNmElmnt.getChildNodes();
				if (hostNm.item(0) == null)
					dbuser.setHost("");
				else
					dbuser.setHost(((Node) hostNm.item(0)).getNodeValue());

				// get port
				NodeList portNmElmntLst = fstElmnt.getElementsByTagName("port");
				Element portNmElmnt = (Element) portNmElmntLst.item(0);
				NodeList portNm = portNmElmnt.getChildNodes();
				if (portNm.item(0) == null)
					dbuser.setPort(-1);
				else
					dbuser.setPort(Integer.parseInt(((Node) portNm.item(0)).getNodeValue()));

				// get username
				NodeList usernameNmElmntLst = fstElmnt.getElementsByTagName("username");
				Element usernameNmElmnt = (Element) usernameNmElmntLst.item(0);
				NodeList usernameNm = usernameNmElmnt.getChildNodes();
				if (usernameNm.item(0) == null)
					dbuser.setUsername("");
				else
					dbuser.setUsername(((Node) usernameNm.item(0)).getNodeValue());

				// get password
				NodeList passwordNmElmntLst = fstElmnt.getElementsByTagName("password");
				Element passwordNmElmnt = (Element) passwordNmElmntLst.item(0);
				NodeList passwordNm = passwordNmElmnt.getChildNodes();
				if (passwordNm.item(0) == null)
					dbuser.setPassword("");
				else
					dbuser.setPassword(((Node) passwordNm.item(0)).getNodeValue());

				// get dbname name
				NodeList dbnameNmElmntLst = fstElmnt.getElementsByTagName("dbname");
				Element dbnameNmElmnt = (Element) dbnameNmElmntLst.item(0);
				NodeList dbnameNm = dbnameNmElmnt.getChildNodes();
				if (dbnameNm.item(0) == null)
					dbuser.setDbname("");
				else
					dbuser.setDbname(((Node) dbnameNm.item(0)).getNodeValue());
			}
			dbList.add(dbuser);
		}
		return dbList;
	}
}

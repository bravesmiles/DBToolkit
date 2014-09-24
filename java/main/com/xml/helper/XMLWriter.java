package com.xml.helper;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.model.DBUser;

/**
 * Write operations with XML. 
 * @author shihe
 */
public class XMLWriter {

	/***
	 * <b>add a conf.</b>
	 * @param filename
	 * @param confname
	 * @return
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws TransformerException 
	 */
	public static void SaveToXML(String confname, DBUser dbUser, String filename) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(filename);
		Element root = document.getDocumentElement();

		// create a user to add to the dbuser.xml.
		Element user = createElement(document, confname, dbUser);
		root.appendChild(user);

		DOMSource source = new DOMSource(document);

		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StreamResult result = new StreamResult(filename);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");  
		transformer.transform(source, result);
	}

	private static Element createElement(Document document, String confname,DBUser dbUser) {
		Element rootElement = document.getDocumentElement();

		
		Element user = document.createElement("user");
		rootElement.appendChild(user);
		
		user.appendChild(document.createTextNode("\n    "));  
		Element confnameElement = document.createElement("confname");
		confnameElement.appendChild(document.createTextNode(confname));
		user.appendChild(confnameElement);

		user.appendChild(document.createTextNode("\n    "));  
		Element databaseElement = document.createElement("database");
		databaseElement.appendChild(document.createTextNode("" + dbUser.getDatabase()));
		user.appendChild(databaseElement);
		
		user.appendChild(document.createTextNode("\n    "));  
		Element hostElement = document.createElement("host");
		hostElement.appendChild(document.createTextNode(dbUser.getHost()));
		user.appendChild(hostElement);

		user.appendChild(document.createTextNode("\n    "));  
		Element portElement = document.createElement("port");
		portElement.appendChild(document.createTextNode("" + dbUser.getPort()));
		user.appendChild(portElement);

		user.appendChild(document.createTextNode("\n    "));  
		Element usernameElement = document.createElement("username");
		usernameElement.appendChild(document.createTextNode(dbUser.getUsername()));
		user.appendChild(usernameElement);

		user.appendChild(document.createTextNode("\n    "));  
		Element passwordElement = document.createElement("password");
		passwordElement.appendChild(document.createTextNode(dbUser.getPassword()));
		user.appendChild(passwordElement);

		user.appendChild(document.createTextNode("\n    "));  
		Element dbnameElement = document.createElement("dbname");
		dbnameElement.appendChild(document.createTextNode(dbUser.getDbname()));
		user.appendChild(dbnameElement);

		return user;
	}

	/***
	 * <b>delete a conf.</b>
	 * @param filename
	 * @param confname
	 *            </br> Refer to : URL
	 *            http://www.blogjava.net/ldz112/archive/2009/12/28/119884.html
	 * @return
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static int DeleteFromXML(String filename, String confname) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder;
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document doc = documentBuilder.parse(filename);

		doc.normalize();
		NodeList links = doc.getElementsByTagName("user");
		// 查找要替换的Node
		boolean blfindNode = false;
		Element link = null;
		for (int i = 0; i < links.getLength(); i++) {
			link = (Element) links.item(i);
			if ((link.getElementsByTagName("confname").item(0)
					.getFirstChild().getNodeValue()).trim()
					.equals(confname)) {
				blfindNode = true;
				try {
					link.getParentNode().removeChild(link);
				} catch (DOMException e) {
					e.printStackTrace();
					return 2;// Dom错误
				}
				break;
			}
		}

		if (!blfindNode) {
			return 1;// 没有找到合适的节点
		}
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			// 设置输出的encoding为改变gb2312

			transformer.setOutputProperty("encoding", "UTF-8");
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(filename);
			transformer.transform(source, result);
		} catch (javax.xml.transform.TransformerConfigurationException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return 3;// 写文件错误
		} catch (javax.xml.transform.TransformerException ex) {
			ex.printStackTrace();
			return 3;
		}

		return 0;// 替换成功
	}

	/***
	 * <b>Rename a conf.</b>
	 * @param filename
	 * @param confname
	 * @param newValue </br>
	 * Refer to : http://www.blogjava.net/ldz112/archive/2009/12/28/119884.html
	 * @return
	 */
	public static int RenameFromXML(String filename, String confname, String newValue) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder;
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document doc = documentBuilder.parse(filename);

			doc.normalize();
			NodeList links = doc.getElementsByTagName("user");
			// 查找要替换的Node
			boolean blfindNode = false;
			Element link = null;
			for (int i = 0; i < links.getLength(); i++) {
				link = (Element) links.item(i);
				if ((link.getElementsByTagName("confname").item(0).getFirstChild()
						.getNodeValue()).trim().equals(confname)) {
					blfindNode = true;
					link.getElementsByTagName("confname").item(0)
							.getFirstChild().setNodeValue(newValue);// 替换node的内容
					break;
				}
			}
			if (!blfindNode) {
				return 1;// 没有找到合适的节点
			}
			try {
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer transformer = tFactory.newTransformer();
				// 设置输出的encoding为改变gb2312
				transformer.setOutputProperty("encoding", "gb2312");
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(filename);
				transformer.transform(source, result);
			} catch (javax.xml.transform.TransformerConfigurationException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				return 3;// 写文件错误
			} catch (javax.xml.transform.TransformerException ex) {
				ex.printStackTrace();
				return 3;
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;// 替换成功
	}
}

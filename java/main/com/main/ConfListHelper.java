package com.main;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.xml.sax.SAXException;

import com.dbutils.common.CONF;
import com.model.DBUser;
import com.xml.helper.XMLReader;

/**
 * Help to get conf list from xml file.
 * @author shihe
 *
 */
public class ConfListHelper {
	/** Add list to confList. */
	public static void addConfLists(org.eclipse.swt.widgets.List confListTmp) {
		List<DBUser> dbuserList = getDBUsers();
		for (DBUser user : dbuserList)
			confListTmp.add(user.getConfname());
	}

	/** Get all db users. */
	public static List<DBUser> getDBUsers() {
		List<DBUser> dbuserList;
		try {
			dbuserList = XMLReader.read(CONF.filename);
			return dbuserList;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			MessageDialog.openError(MainWindow.shlAToolkitFor, "Error", e.getMessage());
		}
		return null;
	}
	
	/** Set Conf content default. */
	public static void clearConfContent()
	{
		MainWindow.databaseCombo.select(0);		MainWindow.txtDbName.setText("");
		MainWindow.txtHost.setText("");			MainWindow.txtPort.setText("");
		MainWindow.txtUsername.setText("");     MainWindow.txtPassword.setText("");
	}
}

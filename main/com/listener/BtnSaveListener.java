package com.listener;

import java.io.IOException;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.xml.sax.SAXException;

import com.dbutils.common.CONF;
import com.main.ConfListHelper;
import com.main.MainWindow;
import com.model.DBUser;
import com.xml.helper.XMLWriter;

/**
 * <b>Button Listener:</b></br>
 * 		Save a personal conf.
 * @author shihe
 */
public class BtnSaveListener extends SelectionAdapter{
	private static Log log = LogFactory.getLog(BtnSaveListener.class);
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		String confname = JOptionPane.showInputDialog("Please input name for your conf.");
		if(confname==null || confname.trim().equals(""))
			confname = "UnnamedDB";
		for(int i=0; i<ConfListHelper.getDBUsers().size();)
		{
			if(ConfListHelper.getDBUsers().get(i).getConfname().equals(confname)) {
				MessageDialog.openInformation(MainWindow.shlAToolkitFor, "Save Conf Error", "The " + confname + " is already existed.");
				confname = JOptionPane.showInputDialog("Please input another name for your conf.");
				if(confname==null || confname.trim().equals(""))	confname = "UnnamedDB";
				i = 0;
			} else 
				i++;
		}
		DBUser user = new DBUser();
		user.setDatabase(MainWindow.databaseCombo.getSelectionIndex());	user.setHost(MainWindow.txtHost.getText());
		user.setPort(Integer.parseInt((MainWindow.txtPort.getText()==null||MainWindow.txtPort.getText().trim().equals(""))?"-1":MainWindow.txtPort.getText()));
		user.setUsername(MainWindow.txtUsername.getText());				user.setPassword(MainWindow.txtPassword.getText());
		user.setDbname(MainWindow.txtDbName.getText());
		try {
			XMLWriter.SaveToXML(confname, user, CONF.filename);
			MessageDialog.openInformation(MainWindow.shlAToolkitFor, "Save conf", "You have add a conf " + confname + "!");
			MainWindow.confList.removeAll();
			ConfListHelper.addConfLists(MainWindow.confList);
			MainWindow.confList.select(MainWindow.confList.getItemCount()-1);
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e1) {
			e1.printStackTrace();
			MessageDialog.openInformation(MainWindow.shlAToolkitFor, "Save conf Error", "An erroe occured when save conf " + confname + "!\n"+e1.getMessage());
			log.warn(new Date().toString() + e1.getMessage()); 
		}
	}
}

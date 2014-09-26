package com.listener;

import java.io.IOException;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

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
 * <b>Button Listener:</b></br> Delete a selected conf.
 * 
 * @author shihe
 */
public class BtnDeleteListener extends SelectionAdapter {
	private static Log log = LogFactory.getLog(BtnDeleteListener.class);

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(MainWindow.confList.getItemCount()>ConfListHelper.getDBUsers().size() && MainWindow.confList.getFocusIndex()==MainWindow.confList.getItemCount()) {
			MessageDialog.openInformation(MainWindow.shlAToolkitFor, "Delete conf Error", "You haven't save the conf " + MainWindow.confList.getItem(MainWindow.confList.getItemCount()-1) + " yet!");
		}
		else {
			int index = MainWindow.confList.getFocusIndex();
			try {
				XMLWriter.DeleteFromXML(CONF.filename, MainWindow.confList.getItem(index));
			} catch (ParserConfigurationException | SAXException | IOException e1) {
				log.warn(new Date().toString() + e1.getMessage()); 
			}
			//Refresh the conflist.
			MainWindow.confList.removeAll();
			ConfListHelper.addConfLists(MainWindow.confList);
			
			DBUser user = null;
			// There is no conf files.
			if(ConfListHelper.getDBUsers()==null || ConfListHelper.getDBUsers().size()==0) {
				ConfListHelper.clearConfContent();
			}
			else { 
				if(index>0) {
					user = ConfListHelper.getDBUsers().get(index-1);
					MainWindow.databaseCombo.select(user.getDatabase());	MainWindow.txtDbName.setText(user.getDbname());
					MainWindow.txtHost.setText(user.getHost());				MainWindow.txtPort.setText(""+user.getPort());
					MainWindow.txtUsername.setText(user.getUsername());  	MainWindow. txtPassword.setText(user.getPassword());
					MainWindow.confList.select(index-1);
				}
				if(index==0) {
					user = ConfListHelper.getDBUsers().get(0);
					MainWindow.databaseCombo.select(user.getDatabase());	MainWindow.txtDbName.setText(user.getDbname());
					MainWindow.txtHost.setText(user.getHost());				MainWindow.txtPort.setText(""+user.getPort());
					MainWindow.txtUsername.setText(user.getUsername());  	MainWindow. txtPassword.setText(user.getPassword());
					MainWindow.confList.select(0);
				}
			}
		}
	}
}

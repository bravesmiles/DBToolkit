package com.listener;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.dbutils.common.DbHelper;
import com.main.MainWindow;
import com.model.DBUser;

/**
 * <b>Button Listener:</b></br>
 * 		Connection Test.
 * @author shihe
 */
public class BtnClickListener extends SelectionAdapter{
	private static Log log = LogFactory.getLog(BtnClickListener.class);
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		
		String host = MainWindow.txtHost.getText().trim();
		String portStr = MainWindow.txtPort.getText().trim();
		
		if (host.equals("")	)//if there is no data in the host or port.				
			MessageDialog.openInformation(MainWindow.shlAToolkitFor,
					"wrong host", "check host first.");
		else if(portStr.equals(""))
			MessageDialog.openInformation(MainWindow.shlAToolkitFor,
					"wrong port", "check port first.");
		else {
			DBUser user = new DBUser();
			int port = Integer.parseInt(portStr);
			user.setDatabase(MainWindow.databaseCombo.getSelectionIndex());
			user.setDbname(MainWindow.txtDbName.getText());;
			user.setHost(host);
			user.setPassword(MainWindow.txtPassword.getText());
			user.setPort(port);
			user.setUsername(MainWindow.txtUsername.getText());
			
			boolean connected;
			try {
				connected = DbHelper.checkIfConnected(user);	// check whether database could be connected.
				if(connected)
					MessageDialog.openInformation(MainWindow.shlAToolkitFor, "Test Connection", "Connected: true!");
			} catch(SQLException se) {
				MessageDialog.openInformation(MainWindow.shlAToolkitFor, "Test Connection", "Connected: false! \n" + se.getMessage());
				log.warn(new Date().toString() + se.getMessage());
			}
		}
	}
}

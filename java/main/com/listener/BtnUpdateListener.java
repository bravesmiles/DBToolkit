package com.listener;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.dbutils.QueryRunner;
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
 * 		Execute the sql query update/delete/insert.
 * @author shihe
 */
public class BtnUpdateListener extends SelectionAdapter {
	private static Log log = LogFactory.getLog(BtnUpdateListenerTest.class);
	private static QueryRunner runner;
	
	@Override
	public void widgetSelected(SelectionEvent event) {
		//If there is no data in the query area.
		if (MainWindow.text.getText() == null	
				|| (MainWindow.text.getText().trim().equals("")))
			MessageDialog.openInformation(MainWindow.shlAToolkitFor,
					"No query in Query Area.", "input query first.");
		//Display the query result.
		else {	
			// Clean the former data.
			while(MainWindow.table.getColumnCount()>0)
				MainWindow.table.getColumns()[0].dispose();
			MainWindow.table.removeAll();

			try {
				String host = MainWindow.txtHost.getText().trim();
				String portStr = MainWindow.txtPort.getText().trim();
				
				int port = Integer.parseInt(portStr);
				DBUser user = new DBUser();
				user.setDatabase(MainWindow.databaseCombo.getSelectionIndex());
				user.setDbname(MainWindow.txtDbName.getText());;
				user.setHost(host);
				user.setPassword(MainWindow.txtPassword.getText());
				user.setPort(port);
				user.setUsername(MainWindow.txtUsername.getText());

				try {
					runner = DbHelper.getQueryRunner(user);
				} catch (SQLException e1) {
					MessageDialog.openInformation(MainWindow.shlAToolkitFor,
							"Error Connection:",
							"Please Check your Connection Test first!");
					log.warn(new Date().toString() + e1.getMessage()); 
					runner = null;
				}
				if (runner != null)
				{
					updateOp();
				}
			} catch (Exception e1) {
				MessageDialog.openInformation(MainWindow.shlAToolkitFor,
						"SQL Execute Error",
						"Execute Error! \n" + e1.getMessage());
				log.warn(new Date().toString() + e1.getMessage()); 
			}
		}
	}
	
	private StringBuffer addToQuery(String value)
	{
		StringBuffer old = MainWindow.query;
		StringBuffer content = new StringBuffer(value.trim()+"\n");

		if(old.indexOf(value.trim()) != -1) {
			old = old.delete(old.indexOf(value), old.indexOf(value)+value.length()+1);
		}
		return content.append(old);
	}
	
	private void updateOp() throws IOException
	{
		int[] result = {};
		int success = 0;
		try{
			// The query execute the update/delete/insert operation
			// How to get SQL and params from UI.
			Object oo[][] = {{111}};
			result  = runner.batch("delete from attr where attr_id=?", oo);
		}
		catch(Exception e){
			log.warn("Errors in runner : " + e.getMessage());
		}
		for(int i=0; i<result.length; i++)
			if(result[i]==1)
				success ++;
			
		MessageDialog.openInformation(MainWindow.shlAToolkitFor, "Result", "Execute " + result.length + (result.length>1?" items":" item") +", and success " + success + ((success>1) ? " items." : " item."));
		MainWindow.query = addToQuery(MainWindow.text.getText());
		com.main.Assistances.addQueryFieldAssist();
	}
}

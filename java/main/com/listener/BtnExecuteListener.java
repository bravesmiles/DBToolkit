package com.listener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.dbutils.common.DbHelper;
import com.main.MainWindow;
import com.model.DBUser;

/**
 * <b>Button Listener:</b></br>
 * 		Execute the sql query.
 * @author shihe
 */
public class BtnExecuteListener extends SelectionAdapter {
	private static Log log = LogFactory.getLog(BtnExecuteListener.class);
	
	@Override
	public void widgetSelected(SelectionEvent event) {
		if (MainWindow.text.getText() == null	//if there is no data in the query area.
				|| (MainWindow.text.getText().trim().equals("")))
			MessageDialog.openInformation(MainWindow.shlAToolkitFor,
					"No query in Query Area.", "input query first.");
		else {	//display the query result.
			MainWindow.table.clearAll();
			MainWindow.table.removeAll();
			MainWindow.table.dispose();

			MainWindow.table = new Table(MainWindow.shlAToolkitFor, SWT.BORDER
					| SWT.FULL_SELECTION);
			MainWindow.table.setBounds(	513,10,
					MainWindow.shlAToolkitFor.getBounds().width
							- MainWindow.text.getBounds().y
							- MainWindow.text.getBounds().width - 115,
					MainWindow.canvas.getBounds().height - 35);
			MainWindow.table.setHeaderVisible(true);
			MainWindow.table.setLinesVisible(true);

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

				QueryRunner runner = null;
				try {
					runner = DbHelper.getQueryRunner(user);
				} catch (SQLException e1) {
					e1.printStackTrace();
					MessageDialog.openInformation(MainWindow.shlAToolkitFor,
							"Error Connection:",
							"Please Check your Connection Test first!");
					log.warn(new Date().toString() + e1.getMessage()); 
					runner = null;
				}
				if (runner != null) {
					try{
					MainWindow.list = runner.query(MainWindow.text.getText(),
							new MapListHandler(), (Object[]) null);
					}
					catch(Exception e){
						System.out.println("Errors in runner : " + e.getMessage());
					}
					long beforeTime = System.currentTimeMillis();
					StringBuffer content = new StringBuffer();

					List<Integer> borderList = new ArrayList<Integer>();
					for (int i = 0; i < MainWindow.list.get(0).size(); i++)
						borderList.add(-1);

					Integer data = 0;
					for (Iterator<Map<String, Object>> li = MainWindow.list.iterator(); li
							.hasNext();) {
						int i = 0;
						Map<String, Object> m = li.next();
						for (Iterator<Entry<String, Object>> mi = m.entrySet()
								.iterator(); mi.hasNext();) {
							Entry<String, Object> e1 = mi.next();
							data = ((e1.getValue() == null || e1.getValue()
									.equals("")) ? 3 : e1.getValue().toString()
									.length() + 3);
							if (data > borderList.get(i))
								borderList.set(i, data);
							i++;
						}
					}

					for (Iterator<Map<String, Object>> li = MainWindow.list.iterator(); li
							.hasNext();) {
						Map<String, Object> m = li.next();

						Set<String> keySet = m.keySet();
						Object[] keys = keySet.toArray();
						for (int i = 0; i < keys.length; i++) {
							content = new StringBuffer("");
							content.append("" + keys[i] + "  ");

							MainWindow.tblclmnResult = new TableColumn(
									MainWindow.table, SWT.CENTER);
							MainWindow.tblclmnResult.setWidth(100);
							MainWindow.tblclmnResult.setText(content.toString());
						}
						break;
					}
					// printf the body of result.
					for (Iterator<Map<String, Object>> li = MainWindow.list.iterator(); li
							.hasNext();) {
						content = new StringBuffer("");
						Map<String, Object> m = li.next();
						for (Iterator<Entry<String, Object>> mi = m.entrySet()
								.iterator(); mi.hasNext();) {
							Entry<String, Object> e1 = mi.next();
							content.append(e1.getValue() + "___");
						}
						String[] st = content.toString().split("___");

						MainWindow.tableItem = new TableItem(MainWindow.table,
								SWT.NONE);
						MainWindow.tableItem.setText(st);
					}

					long afterTime = System.currentTimeMillis();
					content = new StringBuffer("");
					content.append("\n" + MainWindow.list.size()
							+ (MainWindow.list.size() <= 1 ? " row" : " rows")
							+ " in set £¨" + (afterTime - beforeTime)*1.0 / 1000
							+ " sec£©");
					MessageDialog.openInformation(MainWindow.shlAToolkitFor,
							"Result", content.toString());
					MainWindow.query = addToQuery(MainWindow.text.getText());
					com.main.Assistances.addQueryFieldAssist();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
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
}

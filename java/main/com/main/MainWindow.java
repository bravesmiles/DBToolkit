package com.main;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.dbutils.common.CONF;
import com.listener.BtnClickListener;
import com.listener.BtnCreateListener;
import com.listener.BtnDeleteListener;
import com.listener.BtnExecuteListener;
import com.listener.BtnExportListener;
import com.listener.BtnRenameListener;
import com.listener.BtnSaveListener;
import com.listener.ConfListListener;
import com.listener.TxtPortVerifyListener;
import com.model.DBUser;

public class MainWindow {
	private static Log log = LogFactory.getLog(MainWindow.class);
	
	public static StringBuffer query = new StringBuffer("");	// remember the query sentences.
	public static Shell shlAToolkitFor;
	public static Text text;
	public static Text txtHost;
	public static Text txtPort;
	public static Text txtUsername;
	public static Text txtPassword;
	public static Combo databaseCombo;
	public static Text txtDbName;
	public static org.eclipse.swt.widgets.List confList;
	public static Table table;
	public static TableColumn tblclmnResult;
	public static TableItem tableItem;
	public static Button btnCreate;
	public static Button btnDelete;
	public static Button btnRename;
	public static Button btnSave;
	public static Button btnExport;
	public static Button btnExecute;
	public static Button btnClick;
	public static Canvas canvas;
	public static Canvas canvas_2;
	
	public static List<Map<String, Object>> list; // store the result of sql query.
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			query = query.append(Assistances.readFileAsString(CONF.queryfile));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		final Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.open(display);
//					query = query.append(Assistances.readFileAsString(CONF.queryfile));
//					log.info(new Date().toString() + " ≥Ã–Ú∆Ù∂Ø£°");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Open the main window.
	 */
	public void open(Display display) {
		createContents();
		//Resize the main window.
		shlAToolkitFor.addControlListener(new ControlAdapter() {
		      public void controlResized(ControlEvent e) {
		    	  canvas_2.setBounds(10, 10, 129, shlAToolkitFor.getBounds().height-60);
		    	  btnCreate.setBounds(canvas_2.getBounds().x-10, canvas_2.getBounds().height-50, 58, 25);
		    	  btnSave.setBounds(canvas_2.getBounds().x-10, btnCreate.getBounds().y+25, 58, 25);
		    	  btnDelete.setBounds(btnCreate.getBounds().x+71, btnSave.getBounds().y, 58, 25);
		    	  btnRename.setBounds(btnDelete.getBounds().x, btnCreate.getBounds().y, 58, 25);
		    	  confList.setBounds(0, 21, 129, btnCreate.getBounds().y-22);
		    	  
		    	  canvas.setBounds(168, 10, 116, shlAToolkitFor.getBounds().height-60);
		    	  text.setBounds(301, 220, 206, shlAToolkitFor.getBounds().height-270);
		    	  
		  		  table.setBounds(513, 10, shlAToolkitFor.getBounds().width-text.getBounds().y-text.getBounds().width-115, canvas.getBounds().height-35);
		  		  btnExport.setBounds(table.getBounds().x+(table.getBounds().width-25)/2, table.getBounds().y+table.getBounds().height+10, 45, 25);
		      }
		});
		shlAToolkitFor.open();
		shlAToolkitFor.layout();
		while (!shlAToolkitFor.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		
		DBUser defaultUser = new DBUser();
		
		shlAToolkitFor = new Shell();/*SWT.CLOSE|SWT.TITLE|SWT.MIN|SWT.MAX*/
		shlAToolkitFor.addListener(SWT.Close, new Listener() {	//when close the project, save the sql query.
		      public void handleEvent(Event event) {
		    	  try {
					Assistances.writeToText(query+"");
				} catch (IOException e) {
					e.printStackTrace();
				}
		      }
		});
		shlAToolkitFor.setImage(SWTResourceManager.getImage(MainWindow.class, "/image/cisco.jpg"));
		shlAToolkitFor.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		shlAToolkitFor.setText("Toolkit for DBs");
		shlAToolkitFor.setSize(831, 490);
		
/******Middle*Left*Label***********************************/			
		canvas = new Canvas(shlAToolkitFor, SWT.NONE);
		canvas.setBounds(168, 10, 116, 430);
		//Label -- Choose  DataBase
		Label lblDatabase = new Label(canvas, SWT.NONE);
		lblDatabase.setBounds(10, 0, 108, 15);
		lblDatabase.setText("Choose  DataBase :");
		//Label -- Host
		Label lblHost = new Label(canvas, SWT.NONE);
		lblHost.setBounds(80, 31, 37, 15);
		lblHost.setText("Host :");
		//Label -- Port
		Label lblPort = new Label(canvas, SWT.NONE);
		lblPort.setBounds(81, 62, 37, 15);
		lblPort.setText("Port :");
		//Label -- Username
		Label lblUsername = new Label(canvas, SWT.NONE);
		lblUsername.setBounds(45, 93, 73, 15);
		lblUsername.setText(" Username :");
		//Label -- Password
		Label lblPassword = new Label(canvas, SWT.NONE);
		lblPassword.setBounds(55, 120, 63, 15);
		lblPassword.setText("Password :");
		//Label -- Test Connection
		Label lblTestconnection = new Label(canvas, SWT.NONE);
		lblTestconnection.setBounds(20, 191, 96, 15);
		lblTestconnection.setText("TestConnection :");
		//Label -- SQL Query
		Label lblSqlQuery = new Label(canvas, SWT.NONE);
		lblSqlQuery.setBounds(45, 212, 68, 15);
		lblSqlQuery.setText("SQL Query :");
		//Label -- DB name
		Label lblDbname = new Label(canvas, SWT.NONE);
		lblDbname.setBounds(55, 153, 55, 15);
		lblDbname.setText("DBName :");
		
		/******Middle*Right*Text***********************************/		
		//Text -- SQL Query
		text = new Text(shlAToolkitFor, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		text.setMessage("SQL Query.");
		text.setToolTipText("input sql query");
		text.setBackground(new Color(null, new RGB(211, 211, 211)));
		text.setBounds(301, 220, 206, 220);
		
		//Combo -- Choose DataBase
		databaseCombo = new Combo(shlAToolkitFor, SWT.READ_ONLY);
		databaseCombo.setBounds(304, 10, 206, 23);
		databaseCombo.setItems(new String[]{"Oracle", "MySQL", "SQLServer"});
		// Default MySQL
		databaseCombo.select(1);
		
		//Text -- Host
		txtHost = new Text(shlAToolkitFor, SWT.BORDER);
		txtHost.setMessage("host");
		txtHost.setText(defaultUser.getHost());
		txtHost.setToolTipText("input host");
		txtHost.setBackground(new Color(null, new RGB(211, 211, 211)));
		txtHost.setBounds(304, 39, 98, 21);
		
		//Text -- Port
		txtPort = new Text(shlAToolkitFor, SWT.BORDER);
		txtPort.setMessage("port");
		txtPort.setText(defaultUser.getPort() + "");
		txtPort.setTextLimit(5);
		txtPort.setToolTipText("input port num, only 00000-99999 allowed!");
		txtPort.setBackground(new Color(null, new RGB(211, 211, 211)));
		txtPort.setBounds(304, 70, 73, 21);
		txtPort.addVerifyListener(new TxtPortVerifyListener());  
		
		//Text -- Username
		txtUsername = new Text(shlAToolkitFor, SWT.BORDER);
		txtUsername.setMessage("username");
		txtUsername.setText(defaultUser.getUsername());
		txtUsername.setToolTipText("input database user name.");
		txtUsername.setBackground(new Color(null, new RGB(211, 211, 211)));
		txtUsername.setBounds(304, 101, 73, 21);
		
		//Text -- Password
		txtPassword = new Text(shlAToolkitFor, SWT.PASSWORD);
		txtPassword.setMessage("password");
		txtPassword.setText(defaultUser.getPassword());
		txtPassword.setToolTipText("input database password.");
		txtPassword.setBackground(new Color(null, new RGB(211, 211, 211)));
		txtPassword.setBounds(304, 128, 73, 21);
		initDataBindings();
		
		//Text -- Db name
		txtDbName = new Text(shlAToolkitFor, SWT.BORDER);
		txtDbName.setMessage("dbname");
		txtDbName.setToolTipText("input database name.");
		txtDbName.setBackground(new Color(null, new RGB(211, 211, 211)));
		txtDbName.setBounds(304, 162, 73, 21);

		//Button -- Test Connection whether Success
		btnClick = new Button(shlAToolkitFor, SWT.ARROW|SWT.RIGHT);
		btnClick.addSelectionListener(new BtnClickListener());
		btnClick.setBounds(301, 189, 29, 25);

/******Right*Button***********************************/	
		table = new Table(shlAToolkitFor, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(525, 10, 290, 430);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		//Button -- Execute the Query and Display the Result.
		btnExecute = new Button(shlAToolkitFor, SWT.NONE);
		btnExecute.setText("query");
		btnExecute.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		btnExecute.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		btnExecute.addSelectionListener(new BtnExecuteListener());
		btnExecute.setForeground(new Color(null, new RGB(0, 255, 0)));
		btnExecute.setBounds(444, 128, 61, 41);
		
		canvas_2 = new Canvas(shlAToolkitFor, SWT.NONE);
		canvas_2.setBounds(10, 10, 129, shlAToolkitFor.getBounds().height-60);
		
		Label lblSessionControl = new Label(canvas_2, SWT.NONE);
		lblSessionControl.setBounds(10, 0, 106, 15);
		lblSessionControl.setText("Session Control");
		
		//click btnCreate Button to add a conf item to conList.
		btnCreate = new Button(canvas_2, SWT.NONE);
		btnCreate.setBounds(canvas_2.getBounds().x-10, canvas_2.getBounds().height-50, 58, 25);
		btnCreate.addSelectionListener(new BtnCreateListener());
		btnCreate.setText("Create");
		
		//click btnSave Button to save a conf.
		btnSave = new Button(canvas_2, SWT.NONE);
		btnSave.setBounds(canvas_2.getBounds().x-10, btnCreate.getBounds().y+25, 58, 25);
		btnSave.addSelectionListener(new BtnSaveListener());
		btnSave.setText("Save");
		
		//click btnDelete Button to delete the selected conf.
		btnDelete = new Button(canvas_2, SWT.NONE);
		btnDelete.setBounds(btnCreate.getBounds().x+71, btnSave.getBounds().y, 58, 25);
		btnDelete.addSelectionListener(new BtnDeleteListener());
		btnDelete.setText("Delete");
		
		//click btnRename Button to rename the selected conf.
		btnRename = new Button(canvas_2, SWT.NONE);
		btnRename.setBounds(btnDelete.getBounds().x, btnCreate.getBounds().y, 58, 25);
		btnRename.addSelectionListener(new BtnRenameListener());
		btnRename.setText("Rename");
		
		confList = new org.eclipse.swt.widgets.List(canvas_2, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.SINGLE);
		confList.setBounds(0, 21, 129, btnCreate.getBounds().y-22);
		ConfListHelper.addConfLists(confList);
		confList.addSelectionListener(new ConfListListener());
		confList.setToolTipText("click to select the conf.");
		
		btnExport = new Button(shlAToolkitFor, SWT.NONE);
		btnExport.setBounds(639, 388, 45, 25);
		btnExport.addSelectionListener(new BtnExportListener());
		btnExport.setText("Export");
		
		if(ConfListHelper.getDBUsers()!=null && ConfListHelper.getDBUsers().size()!=0)
		{
			confList.select(0);
			databaseCombo.select(ConfListHelper.getDBUsers().get(0).getDatabase());	txtDbName.setText(ConfListHelper.getDBUsers().get(0).getDbname());
			txtHost.setText(ConfListHelper.getDBUsers().get(0).getHost());			txtPort.setText(""+ConfListHelper.getDBUsers().get(0).getPort());
			txtUsername.setText(ConfListHelper.getDBUsers().get(0).getUsername());  txtPassword.setText(ConfListHelper.getDBUsers().get(0).getPassword());
		}
		
		try {
			new Assistances();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeBackgroundShlAToolkitForObserveWidget = WidgetProperties.background().observe(shlAToolkitFor);
		IObservableValue blueShlAToolkitForgetBackgroundObserveValue = PojoProperties.value("blue").observe(shlAToolkitFor.getBackground());
		bindingContext.bindValue(observeBackgroundShlAToolkitForObserveWidget, blueShlAToolkitForgetBackgroundObserveValue, null, null);
		//
		IObservableValue observeBackgroundShlAToolkitForObserveWidget_1 = WidgetProperties.background().observe(shlAToolkitFor);
		IObservableValue rGBShlAToolkitForgetBackgroundObserveValue = PojoProperties.value("RGB").observe(shlAToolkitFor.getBackground());
		bindingContext.bindValue(observeBackgroundShlAToolkitForObserveWidget_1, rGBShlAToolkitForgetBackgroundObserveValue, null, null);
		//
		return bindingContext;
	}
}

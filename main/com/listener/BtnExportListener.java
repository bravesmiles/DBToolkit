package com.listener;

import java.io.IOException;
import java.util.Date;

import jxl.write.WriteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DirectoryDialog;

import com.main.MainWindow;
import com.xml.helper.TableToExcel;

/**
 * <b>Button Listener:</b></br>
 * 		Export the query result as a excel file(.xls) into a user-defined direction.
 * @author shihe
 */
public class BtnExportListener extends SelectionAdapter{
	private static Log log = LogFactory.getLog(BtnExportListener.class);
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(MainWindow.list==null || MainWindow.list.size()==0)
			MessageDialog.openWarning(MainWindow.shlAToolkitFor, "Export Warning!", "There is no data of query.");
		else {
			DirectoryDialog dd = new DirectoryDialog(MainWindow.shlAToolkitFor);
			dd.setText("File Saved");
			dd.setMessage("Select a directory");
			String dir = dd.open();
			if(dir != null)
			{
				try {
					TableToExcel.export(dir+"\\result.xls", MainWindow.list);	//export to the user-defined directory.
				} catch (WriteException | IOException e1) {
					e1.printStackTrace();
					log.warn(new Date().toString() + e1.getMessage()); 
				}
				MessageDialog.openInformation(MainWindow.shlAToolkitFor, "Export Success!", "Query result has been saved to " + dir + "\\result.xls");
				log.info(new Date().toString() + " Query result has been saved to " + dir + "\\result.xls"); 
				
			}
		}
	}
}

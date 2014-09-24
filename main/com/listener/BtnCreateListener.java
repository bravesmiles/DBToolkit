package com.listener;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.main.ConfListHelper;
import com.main.MainWindow;

/**
 * <b>Button Listener:</b></br>
 * 		Create a personal conf.
 * @author shihe
 */
public class BtnCreateListener extends SelectionAdapter{
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		//If the newest conf haven't saved, remind user to save it first.
		if(MainWindow.confList.getItemCount()>ConfListHelper.getDBUsers().size())
			MessageDialog.openInformation(MainWindow.shlAToolkitFor, "Create conf Error", "You haven't save the conf " + MainWindow.confList.getItem(MainWindow.confList.getItemCount()-1) + " yet!");
		else {
			MainWindow.confList.add("UnnamedDB", MainWindow.confList.getItemCount());
			//clear the data in the input text area.
			MainWindow.confList.select(MainWindow.confList.getItemCount()-1);
			MainWindow.databaseCombo.select(0);		MainWindow.txtDbName.setText("");
			MainWindow.txtHost.setText("");			MainWindow.txtPort.setText("");
			MainWindow.txtUsername.setText("");     MainWindow.txtPassword.setText("");
		}
	}
}

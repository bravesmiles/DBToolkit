package com.listener;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.main.ConfListHelper;
import com.main.MainWindow;
import com.model.DBUser;

/**
 * <b>List Listener:</b></br>
 * 		Display the content of the selected conf.
 * @author shihe
 */
public class ConfListListener extends SelectionAdapter{
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		int listHaveChouse = MainWindow.confList.getSelectionIndex();  
		DBUser selected = new DBUser();
		//Did not add a new conf.
		if(MainWindow.confList.getItemCount() == ConfListHelper.getDBUsers().size())
		{
			if(listHaveChouse==-1)
				listHaveChouse = MainWindow.confList.getItemCount()-1;
			selected = ConfListHelper.getDBUsers().get(listHaveChouse);
			MainWindow.txtHost.setText(selected.getHost());
			MainWindow.txtPort.setText(""+selected.getPort());
			MainWindow.txtUsername.setText(selected.getUsername());
			MainWindow.txtPassword.setText(selected.getPassword());
			MainWindow.txtDbName.setText(selected.getDbname());
			MainWindow.databaseCombo.select(selected.getDatabase());
		}
		//Add a new conf but have not saved.
		else { 
			if(listHaveChouse==-1)
			{
				listHaveChouse = MainWindow.confList.getItemCount()-2;
				MainWindow.confList.select(listHaveChouse+1);
				ConfListHelper.clearConfContent();
			}
			else if(listHaveChouse>=MainWindow.confList.getItemCount()-1)
			{
				listHaveChouse = MainWindow.confList.getItemCount()-1;
				MainWindow.confList.select(listHaveChouse);
				ConfListHelper.clearConfContent();
			} else {
				selected = ConfListHelper.getDBUsers().get(listHaveChouse);
				MainWindow.txtHost.setText(selected.getHost());
				MainWindow.txtPort.setText(""+selected.getPort());
				MainWindow.txtUsername.setText(selected.getUsername());
				MainWindow.txtPassword.setText(selected.getPassword());
				MainWindow.txtDbName.setText(selected.getDbname());
				MainWindow.databaseCombo.select(selected.getDatabase());
			}
		}
	}
}

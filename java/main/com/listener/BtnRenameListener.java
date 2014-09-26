package com.listener;

import javax.swing.JOptionPane;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.dbutils.common.CONF;
import com.main.ConfListHelper;
import com.main.MainWindow;
import com.xml.helper.XMLWriter;

/**
 * <b>Button Listener:</b></br> Rename the selected conf.
 * 
 * @author shihe
 */
public class BtnRenameListener extends SelectionAdapter {

	@Override
	public void widgetSelected(SelectionEvent e) {
		// If the newest conf haven't saved, remind user to save it first.
		if (MainWindow.confList.getItemCount() > ConfListHelper.getDBUsers()
				.size()) {
			MessageDialog.openInformation(
					MainWindow.shlAToolkitFor,
					"Rename conf Error",
					"You haven't save the conf "
							+ MainWindow.confList.getItem(MainWindow.confList
									.getItemCount() - 1) + " yet!");
		} else {
			String confname = JOptionPane
					.showInputDialog("Please input new name for the conf.");
			// If user click 'OK' without input nothing or click 'CANCEL'
			if (confname == null || confname.trim().equals("")) {
				confname = "UnnamedDB";
			}
			for (int i = 0; i < ConfListHelper.getDBUsers().size();) {
				// If name input is the same as someone existed, error.
				if (ConfListHelper.getDBUsers().get(i).getConfname()
						.equals(confname)) {
					MessageDialog.openInformation(MainWindow.shlAToolkitFor,
							"Rename Conf Error", "The " + confname
									+ " is already existed.");
					confname = JOptionPane
							.showInputDialog("Please input another name for your conf.");
					if (confname == null || confname.trim().equals(""))
						confname = "UnnamedDB";
					i = 0;
				} else
					i++;
			}
			// Rename conf
			XMLWriter.RenameFromXML(CONF.filename, MainWindow.confList
					.getItem(MainWindow.confList.getFocusIndex()), confname);
			MainWindow.confList.removeAll();
			ConfListHelper.addConfLists(MainWindow.confList);
		}
	}
}

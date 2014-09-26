package com.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.TextContentAdapter;

import com.dbutils.common.CONF;

/**
 * Input text auto-completed
 */
public class Assistances {

	public Assistances() throws IOException {
		addHostFieldAssist();
		addPortFieldAssist();
		addQueryFieldAssist();
	}

	/**
	 * host input assistance
	 */
	private void addHostFieldAssist() {
		new AutoCompleteField(MainWindow.txtHost, new TextContentAdapter(),
				new String[] { "127.0.0.1", "localhost" });
	}

	/**
	 * port input assistance
	 */
	private void addPortFieldAssist() {
		new AutoCompleteField(MainWindow.txtPort, new TextContentAdapter(),
				new String[] { "3306", "1521", "1433" });
	}

	public static void addQueryFieldAssist() throws IOException {
		String[] proposals;
		String proposal = MainWindow.query + "";
		if (proposal == null || proposal.equals(""))
			proposals = new String[] { "select * from " };
		else
			proposals = proposal.split("\n");

		new AutoCompleteField(MainWindow.text, new TextContentAdapter(),
				proposals);
	}

	public static String readFileAsString(String filename) throws IOException {
		return FileUtils.readFileToString(new File(filename));
	}

	public static void writeToText(String value) throws IOException {
		StringBuffer old = MainWindow.query;
		StringBuffer content = new StringBuffer(value.trim() + "\n");

		if (old.indexOf(value.trim()) != -1) {
			old = old.delete(old.indexOf(value),
					old.indexOf(value) + value.length() + 1);
		}
		content.append(old);

		// the true will append the new data
		FileWriter fw = new FileWriter(CONF.queryfile, false);
		// override the content of query file
		fw.write(content + "");
		fw.close();
	}
}

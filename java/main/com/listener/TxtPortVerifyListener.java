package com.listener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

/**
 * <b>Verify Listener:</b></br>
 * 		Verify the content input.
 * @author shihe
 */
public class TxtPortVerifyListener implements VerifyListener {

	public void verifyText(VerifyEvent e) {
		Pattern pattern = Pattern.compile("[0-9]\\d*");
		Matcher matcher = pattern.matcher(e.text);
		// Deal with digit
		if (matcher.matches()) 
			e.doit = true;
		// Have character, include blank or Chinese
		else if (e.text.length() > 0) 
			e.doit = false;
		else
			e.doit = true;
	}
}

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
		if (matcher.matches()) // 处理数字
			e.doit = true;
		else if (e.text.length() > 0) // 有字符情况,包含中文、空格
			e.doit = false;
		else
			e.doit = true;
	}
}

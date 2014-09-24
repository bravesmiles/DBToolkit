package com.helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class TestVersion {
	private static Log log = LogFactory.getLog(TestVersion.class);

	@Test
	public void test() {

		log.info("info");
		log.debug("debug");
		log.warn("warn");
		log.error("error");

	}

}

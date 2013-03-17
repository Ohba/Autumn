package com.ohba.autumn;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AutumnConfigTest {

	@Test
	public void shouldAllowNoQuotes() {
		String configJson = "{pojoMapping:false}";
		AutumnConfig result = AutumnConfig.fromJsonString(null,configJson);
		assertNotNull(result);
		assertFalse(result.getPojoMapping());
	}

	@Test
	public void shouldAllowSingleQuotes() {
		String configJson = "{'pojoMapping':false}";
		AutumnConfig result = AutumnConfig.fromJsonString(null, configJson);
		assertNotNull(result);
		assertFalse(result.getPojoMapping());
	}

	@Test
	public void shouldAllowComments() {
		String configJson = "{'pojoMapping' /* comment*/ :false} // other comment";
		AutumnConfig result = AutumnConfig.fromJsonString(null, configJson);
		assertNotNull(result);
		assertFalse(result.getPojoMapping());
	}
	
}

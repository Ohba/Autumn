package com.ohba.autumn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class AutumnConfigTest {

	@Test
	public void shouldAllowNoQuotes() {
		String configJson = "{pojoMapping:false}";
		AutumnConfig result = AutumnConfig.fromJsonString(configJson);
		assertNotNull(result);
		assertFalse(result.getPojoMapping());
	}

	@Test
	public void shouldAllowSingleQuotes() {
		String configJson = "{'pojoMapping':false}";
		AutumnConfig result = AutumnConfig.fromJsonString(configJson);
		assertNotNull(result);
		assertFalse(result.getPojoMapping());
	}

	@Test
	public void shouldAllowComments() {
		String configJson = "{'pojoMapping' /* comment*/ :false} // other comment";
		AutumnConfig result = AutumnConfig.fromJsonString(configJson);
		assertNotNull(result);
		assertFalse(result.getPojoMapping());
	}
	
	@Test
	public void shouldHavePackages() {
		String configJson = "{resourcePackages:'com.ohba',pojoMapping:false}";
		AutumnConfig result = AutumnConfig.fromJsonString(configJson);
		assertNotNull(result);
		assertEquals(result.toInitParams().get(PackagesResourceConfig.PROPERTY_PACKAGES), "com.ohba");
		assertEquals(result.toInitParams().get(JSONConfiguration.FEATURE_POJO_MAPPING), "false");
		}

}

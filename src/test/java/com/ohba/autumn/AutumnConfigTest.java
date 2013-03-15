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
		String configJson = "{pathPackage:'com.ohba',pojoMapping:false}";
		AutumnConfig result = AutumnConfig.fromJsonString(configJson);
		assertNotNull(result);
		assertEquals("com.ohba", result.getJerseyInitParams().get(PackagesResourceConfig.PROPERTY_PACKAGES));
		assertEquals("false", result.getJerseyInitParams().get(JSONConfiguration.FEATURE_POJO_MAPPING));
		}

}

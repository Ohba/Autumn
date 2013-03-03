package com.ohba.autumn;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;

@Data
public class AutumnConfig {
	
	private String resourcePackages;
	private Boolean pojoMapping = true;
	
	// cant imagine why you would want to disable the POJO-JSON mapping
	// im just testing the capability of the Config pattern here
	// as well as the bility to set defaults

	public static AutumnConfig readFrom(String configFilename) {
		
		// TODO expand capabilities to try the input in this order:
		// getResourceAsStream(String)
		// new URI(String)
		//    if schema == file:, new File(URI)
		//    if schema == http(s):, GET request the JSON
		
		InputStream configStream = AutumnConfig.class.getClassLoader().getResourceAsStream(configFilename);
		try {
			ObjectMapper mapper = new ObjectMapper();
			configureMapperFeatures(mapper);
			return mapper.readValue(configStream, AutumnConfig.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * hoping to make the reading of config.json file
	 * a little more forgiving for newbies
	 * @param mapper
	 */
	private static void configureMapperFeatures(ObjectMapper mapper) {
		mapper
		
			// when storing into an Array if you only get a String automatically place it in an Array
			.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
		
			// allow comments in the JSON
			.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_COMMENTS, true)
			
			// allow field names or values to be surrounded with single quotes,
			// more javascript like
			.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
			
			// allow NO quotes around field names,
			// more javascript like
			.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
			
		;
	}

	public Map<String, String> toInitParams() {
		Map<String, String> propertyBag = new HashMap<>();
		propertyBag.put(PackagesResourceConfig.PROPERTY_PACKAGES, resourcePackages);
		propertyBag.put(JSONConfiguration.FEATURE_POJO_MAPPING, pojoMapping.toString());
		return propertyBag;
	}

}

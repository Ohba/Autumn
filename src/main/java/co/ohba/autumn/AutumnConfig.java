package co.ohba.autumn;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import lombok.Data;
import lombok.val;

import org.codehaus.jackson.map.ObjectMapper;

import co.ohba.autumn.utils.NullAwareBeanUtils;

/*
 * default values for this class ARE NOT stored in the java,
 * but in autumn.defaults.json
 */
@Data
public class AutumnConfig {
	
	private List<String> pathPackage;
	private String entityPackage;
	private Boolean pojoMapping;
	
	private DataStoreType dataStoreType;
	private Jdbc jdbc;
	private Mongo mongo;
	
	public static enum DataStoreType {
		JDBC, MONGO
	}
	
	@Data
	public static class Jdbc {
		private String driver, url, user, password;
	}
	
	@Data
	public static class Mongo {
		private String port, host, db, user, password;
	}
	
	// cant imagine why you would want to disable the POJO-JSON mapping
	// im just testing the capability of the Config pattern here
	// as well as the ability to set defaults
	
	public static AutumnConfig fromResource(String defaultConfigFilename, String configFilename) {
		InputStream defaultConfigStream = defaultConfigFilename==null ? null :
			AutumnConfig.class.getClassLoader().getResourceAsStream(defaultConfigFilename);
		InputStream configStream = AutumnConfig.class.getClassLoader().getResourceAsStream(configFilename);
		return fromStream(defaultConfigStream, configStream);
	}
	
	public static AutumnConfig fromJsonString(String defaultConfigJson, String configJson) {
		InputStream defaultConfigStream = defaultConfigJson==null? null : 
			new ByteArrayInputStream(defaultConfigJson.getBytes());
		InputStream configStream = new ByteArrayInputStream(configJson.getBytes());
		return fromStream(defaultConfigStream, configStream);
	}
	
	public static AutumnConfig fromStream(InputStream defaultConfigStream, InputStream configStream) {
		ObjectMapper mapper = new ObjectMapper();
		configureMapperFeatures(mapper);
		try {
			val baseConfig = defaultConfigStream==null ? new AutumnConfig() :
				mapper.readValue(defaultConfigStream, AutumnConfig.class);
			val overlayConfig = mapper.readValue(configStream, AutumnConfig.class);
			NullAwareBeanUtils.INSTANCE.copyProperties(baseConfig, overlayConfig);
			return baseConfig;
			// YES! return the baseConfig, because by now
			// it has already been overlayed with the users values
		} catch (IOException  e) {
			throw new RuntimeException(e);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * we change some jackson features in an attempt
	 * to make the reading of `autumn.json` file
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

}

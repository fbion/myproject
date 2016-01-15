package com.sinoiov.pay.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public final class JSONUtil {
	static public String object2JSON(Object object) throws JsonGenerationException, JsonMappingException, IOException {
		Writer strWriter = new StringWriter();//Can not close the stream
		new ObjectMapper().writeValue(strWriter, object);
		return strWriter.toString();
	}

	static public Object json2Object(String json, Class<?> clazz) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(json, clazz);
	}
}

package com.ctfo.upp.gateway.fastpay.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;

public final class JSONUtil {
	static public String object2JSON(Object object) throws JsonGenerationException, JsonMappingException, IOException {
		final Writer strWriter = new StringWriter();// Can not close the stream
		new ObjectMapper() {
			private ObjectMapper configure() {
				this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, false);
				this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS,true);
				this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {

					@Override
					public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
						// TODO Auto-generated method stub
						arg1.writeString("");
					}

				});
				return this;
			}
		}.configure().writeValue(strWriter, object);
		return strWriter.toString();
	}

	static public Object json2Object(String json, Class<?> clazz) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(json, clazz);
	}
}

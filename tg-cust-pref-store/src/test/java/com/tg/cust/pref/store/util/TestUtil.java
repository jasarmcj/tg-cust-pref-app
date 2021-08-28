package com.tg.cust.pref.store.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author jasar
 *
 */
public class TestUtil {
	
	public static String getJSONArrayStringByJSONFileName(Resource resource) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		InputStream resourceAsStream = resource.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
		return ((JSONArray) parser.parse(bufferedReader)).toString();
	}
	
	public static String getJSONObjectStringByJSONFileName(Resource resource) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		InputStream resourceAsStream = resource.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
		return ((JSONObject) parser.parse(bufferedReader)).toString();
	}

	public static String getJSONArrayStringByJSONFileName(String jsonFileName) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		InputStream resourceAsStream = TestUtil.class.getResourceAsStream("/testpayloads/" + jsonFileName);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
		return ((JSONArray) parser.parse(bufferedReader)).toString();
	}
	
	public static String getJSONObjectStringByJSONFileName(String jsonFileName) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		InputStream resourceAsStream = TestUtil.class.getResourceAsStream("/testpayloads/" + jsonFileName);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
		return ((JSONObject) parser.parse(bufferedReader)).toString();
	}
	
	public static <T> T mapResult(String stringValue, Class<T> type) throws IOException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(stringValue, type);
	}
	
	public static <T> T mapResult(String stringValue, TypeReference<T> typeRef) throws IOException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(stringValue, typeRef);
		//return mapper.readValue(stringValue, type);
	}
}

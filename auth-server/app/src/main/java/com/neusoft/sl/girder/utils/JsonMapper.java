package com.neusoft.sl.girder.utils;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

/**
 * Json对象转换辅助工具
 * 
 * <pre>
 * 将Object对象转换为json串
 * </pre>
 * 
 * @author wuyf
 * 
 */
public class JsonMapper {

	private JsonMapper() {
		// hide for utils class
	}

	/**
	 * 将Object转换为String
	 * 
	 * @param dto
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String toJson(Object object) {
		if (null == object) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new JsonMapperException("将Object转换为String错误", e);
		}
	}

	/**
	 * 将json串转换为Object
	 * 
	 * @param content
	 * @param valueType
	 * @return
	 * @throws IOException
	 */
	public static <T> T fromJson(String content, Class<T> valueType) {
		if (null == content) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(content, valueType);
		} catch (IOException e) {
			throw new JsonMapperException("将json串转换为Object错误", e);
		}
	}

	public static <T> T fromJson(String content, TypeReference<T> valueType) {
		if (null == content) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(content, valueType);
		} catch (IOException e) {
			throw new JsonMapperException("将json串转换为Object错误", e);
		}
	}

	public static JsonNode fromJsonNode(String content) {
		if (null == content) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readTree(content);
		} catch (IOException e) {
			throw new JsonMapperException("将json串转换为JsonNode错误", e);
		}
	}

	public static String getJsonNodeValue(String key, JsonNode jsonNode) {
		JsonNode dataNode = jsonNode.get(key);
		if (dataNode == null)
			return "";
		JsonNodeType type = dataNode.getNodeType();
		switch (type) {
		case OBJECT:
			return dataNode.toString();
		case STRING:
			return dataNode.asText();
		default:
			return "";
		}
	}

}

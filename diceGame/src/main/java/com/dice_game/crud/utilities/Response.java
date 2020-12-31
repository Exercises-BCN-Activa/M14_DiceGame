package com.dice_game.crud.utilities;

import java.util.HashMap;

public class Response {
	
	public static Response error(String message) {
		Response response = new Response();
		response.put("success", false);
		response.put("message", message);
		return response;
	}
	
	public static Response success(String message, Object content) {
		Response response = new Response();
		response.put("success", true);
		response.put("message", message);
		response.put("content", content);
		return response;
	}
	
	public String getMessage() {
		return (String) get("message");
	}
	
	public void addExceptionToMessage(Exception exception) {
		put("message", getMessage().concat(exception.getMessage()));
	}
	
	public boolean isSuccess() {
		return (boolean) get("success");
	}
	
	public Object getContent() {
		return (map.containsKey("content")) ? get("content") : CONTENT_ERROR;
	}
	
	@Override
	public String toString() {
		return "Response : " + map;
	}
	
	private Response() {
		map = new HashMap<>();
	}
	
	private void put(String key, Object value) {
		map.put(key, value);
	}
	
	private Object get(String key) {
		return map.get(key);
	}
	
	private final HashMap<String, Object> map;
	private final String CONTENT_ERROR = "Error response have no content!";
}

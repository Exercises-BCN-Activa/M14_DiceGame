package com.dice_game.crud.utilities;

import java.util.HashMap;

public class Response {
	
	public static Response error(String message) {
		Response response = new Response();
		response.put(SUCCESS, false);
		response.put(MESSAGE, message);
		return response;
	}
	
	public static Response success(String message, Object content) {
		Response response = new Response();
		response.put(SUCCESS, true);
		response.put(MESSAGE, message);
		response.put(CONTENT, content);
		return response;
	}
	
	public void addExceptionToMessage(Exception exception) {
		String oldMessage = getMessage();
		String exceptMessage = catchMsg(exception.getMessage());
		String newMessage = oldMessage.concat(exceptMessage);
		put(MESSAGE, newMessage);
	}
	
	public String getMessage() {
		return (String) get(MESSAGE);
	}
	
	private String catchMsg(String exceptionMsg) {
		return (Util.notNullOrEmpty(exceptionMsg)) ? exceptionMsg : EXCEPTION_MSG_ERROR;
	}
	
	public boolean isSuccess() {
		return (boolean) get(SUCCESS);
	}
	
	public Object getContent() {
		return (map.containsKey(CONTENT)) ? get(CONTENT) : CONTENT_ERROR;
	}

	@Override
	public String toString() {
		return "Response : {"
				+ "success : " + isSuccess() 
				+ ", message : " + getMessage() 
				+ ", content : " + getContent() 
				+ "}";
	}

	private Response() {
		map = new HashMap<String, Object>();
	}
	
	private void put(String key, Object value) {
		map.put(key, value);
	}
	
	private Object get(String key) {
		return map.get(key);
	}
	
	private final HashMap<String, Object> map;
	private final String CONTENT_ERROR = "Error Response have no content!";
	private final String EXCEPTION_MSG_ERROR = "No Exception Messages Available!";
	private final static String SUCCESS = "success";
	private final static String MESSAGE = "message";
	private final static String CONTENT = "content";
}

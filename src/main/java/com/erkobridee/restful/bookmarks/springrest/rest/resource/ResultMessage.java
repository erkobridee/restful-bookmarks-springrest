package com.erkobridee.restful.bookmarks.springrest.rest.resource;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultMessage implements Serializable {

	// --------------------------------------------------------------------------
	private static final long serialVersionUID = 1480386631918642994L;
	// --------------------------------------------------------------------------
	
	private int code;
	private String message;

	public ResultMessage() {
		super();
	}

	public ResultMessage(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

package com.appchallenge.dto;

import java.io.Serializable;

public class RespuestaApi implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status;
	private Object body;
	
	public RespuestaApi() {
		super();
	}

	public RespuestaApi(String status, Object body) {
		super();
		this.status = status;
		this.body = body;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

}

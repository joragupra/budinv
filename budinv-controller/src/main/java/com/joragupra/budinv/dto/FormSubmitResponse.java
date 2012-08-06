package com.joragupra.budinv.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class FormSubmitResponse {
	
	private boolean ok;
	
	private String message;
	
	public FormSubmitResponse(){
		this(true, null);
	}
	
	public FormSubmitResponse(boolean ok, String message){
		this.ok = ok;
		this.message = message;
	}
	
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}

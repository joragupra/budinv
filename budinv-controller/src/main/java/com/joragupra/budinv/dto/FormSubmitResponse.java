package com.joragupra.budinv.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public record FormSubmitResponse(boolean ok, String message) {
	public FormSubmitResponse() {
		this(true, null);
	}
}

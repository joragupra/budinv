package com.joragupra.budinv.dto;

import java.time.LocalDate;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "calevent")
public class Event {

	private Long id;
	private String title;
	private LocalDate start;
	private LocalDate end;
	private String url;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public LocalDate getStart() { return start; }
	public void setStart(LocalDate start) { this.start = start; }

	public LocalDate getEnd() { return end; }
	public void setEnd(LocalDate end) { this.end = end; }

	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
}

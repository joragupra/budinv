package com.joragupra.budinv.dto;


public class Income extends BookkeepingEntry {
	
	private final String entryType = "INCOME";
	
	@Override
	public String getEntryType() {
		return entryType;
	}

}

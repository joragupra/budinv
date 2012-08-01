package com.joragupra.budinv.dto;

public class IncurredExpense extends BookkeepingEntry {
	
	private final String entryType = "EXPENSE";
	
	@Override
	public String getEntryType() {
		return entryType;
	}

}

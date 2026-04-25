package com.joragupra.budinv.dto;

public final class IncurredExpense extends BookkeepingEntry {

	@Override
	public String getEntryType() {
		return "EXPENSE";
	}
}

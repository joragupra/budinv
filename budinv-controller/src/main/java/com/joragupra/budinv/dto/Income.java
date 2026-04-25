package com.joragupra.budinv.dto;

public final class Income extends BookkeepingEntry {

	@Override
	public String getEntryType() {
		return "INCOME";
	}
}

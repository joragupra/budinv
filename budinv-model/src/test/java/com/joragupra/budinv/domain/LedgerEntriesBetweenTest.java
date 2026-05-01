package com.joragupra.budinv.domain;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LedgerEntriesBetweenTest {

	@Test
	void getEntriesBetweenDates_excludesEntryBeforeFrom() {
		LocalDate from = LocalDate.of(2024, 3, 1);
		LocalDate to = LocalDate.of(2024, 3, 31);
		Ledger ledger = new Ledger();

		Income beforeFrom = new Income();
		beforeFrom.setAmount(50.0);
		beforeFrom.setIncurredDate(LocalDate.of(2024, 2, 28));
		ledger.bookEntry(beforeFrom);

		Income inRange = new Income();
		inRange.setAmount(100.0);
		inRange.setIncurredDate(LocalDate.of(2024, 3, 15));
		ledger.bookEntry(inRange);

		List<BookkeepingEntry> result = ledger.getEntriesBetweenDates(from, to);
		assertEquals(1, result.size());
		assertEquals(inRange, result.get(0));
	}

	@Test
	void getEntriesBetweenDates_loopExitsNaturally_whenNoEntryAfterTo() {
		LocalDate from = LocalDate.of(2024, 3, 1);
		LocalDate to = LocalDate.of(2024, 3, 31);
		Ledger ledger = new Ledger();

		Income first = new Income();
		first.setAmount(100.0);
		first.setIncurredDate(LocalDate.of(2024, 3, 5));
		ledger.bookEntry(first);

		Income second = new Income();
		second.setAmount(200.0);
		second.setIncurredDate(LocalDate.of(2024, 3, 20));
		ledger.bookEntry(second);

		List<BookkeepingEntry> result = ledger.getEntriesBetweenDates(from, to);
		assertEquals(2, result.size());
	}
}

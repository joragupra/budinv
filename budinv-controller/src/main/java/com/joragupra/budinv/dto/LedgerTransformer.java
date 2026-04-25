package com.joragupra.budinv.dto;

import java.util.List;

public class LedgerTransformer {

	public static com.joragupra.budinv.dto.Ledger domainToDto(com.joragupra.budinv.domain.Ledger domain) {
		com.joragupra.budinv.dto.Ledger dto = new com.joragupra.budinv.dto.Ledger();
		dto.setFrom(domain.getFrom());
		dto.setTo(domain.getTo());
		dto.setTotalIncome(domain.calculateIncome());
		dto.setTotalExpense(domain.calculateExpense());
		dto.setBalance(domain.calculateBalance());
		List<com.joragupra.budinv.dto.BookkeepingEntry> entries = domain.getEntries().stream()
				.map(LedgerTransformer::mapEntry)
				.toList();
		dto.setEntries(entries);
		return dto;
	}

	public static com.joragupra.budinv.domain.Ledger dtoToDomain(com.joragupra.budinv.dto.Ledger dto) {
		var domain = new com.joragupra.budinv.domain.Ledger(dto.getFrom(), dto.getTo());
		if (dto.getEntries() != null) {
			dto.getEntries().forEach(e -> domain.bookEntry(mapEntryToDomain(e)));
		}
		return domain;
	}

	public static com.joragupra.budinv.dto.Event domainToEvent(com.joragupra.budinv.domain.BookkeepingEntry entry) {
		var event = new com.joragupra.budinv.dto.Event();
		event.setId(entry.getId());
		event.setStart(entry.getIncurredDate());
		event.setEnd(entry.getIncurredDate());
		return event;
	}

	private static com.joragupra.budinv.dto.BookkeepingEntry mapEntry(com.joragupra.budinv.domain.BookkeepingEntry entry) {
		com.joragupra.budinv.dto.BookkeepingEntry dto = switch (entry) {
			case com.joragupra.budinv.domain.Income i -> new com.joragupra.budinv.dto.Income();
			case com.joragupra.budinv.domain.IncurredExpense e -> new com.joragupra.budinv.dto.IncurredExpense();
		};
		dto.setId(entry.getId());
		dto.setAmount(entry.getAmount());
		dto.setComments(entry.getComments());
		dto.setLogDate(entry.getLogDate());
		dto.setIncurredDate(entry.getIncurredDate());
		return dto;
	}

	private static com.joragupra.budinv.domain.BookkeepingEntry mapEntryToDomain(com.joragupra.budinv.dto.BookkeepingEntry entry) {
		com.joragupra.budinv.domain.BookkeepingEntry domain = switch (entry) {
			case com.joragupra.budinv.dto.Income i -> new com.joragupra.budinv.domain.Income();
			case com.joragupra.budinv.dto.IncurredExpense e ->
					new com.joragupra.budinv.domain.IncurredExpense(null);
		};
		domain.setId(entry.getId());
		domain.setAmount(entry.getAmount());
		domain.setComments(entry.getComments());
		domain.setLogDate(entry.getLogDate());
		domain.setIncurredDate(entry.getIncurredDate());
		return domain;
	}
}

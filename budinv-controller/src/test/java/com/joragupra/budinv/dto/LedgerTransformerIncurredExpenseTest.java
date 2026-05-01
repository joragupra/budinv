package com.joragupra.budinv.dto;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LedgerTransformerIncurredExpenseTest {

	@Test
	void dtoToDomain_mapsIncurredExpenseEntry() {
		LocalDate date = LocalDate.of(2024, 5, 10);
		Ledger dto = new Ledger();
		dto.setFrom(date);
		dto.setTo(date);

		IncurredExpense expenseDto = new IncurredExpense();
		expenseDto.setId(5L);
		expenseDto.setAmount(75.0);
		expenseDto.setIncurredDate(date);
		dto.setEntries(List.of(expenseDto));

		com.joragupra.budinv.domain.Ledger domain = LedgerTransformer.dtoToDomain(dto);

		assertEquals(1, domain.getEntries().size());
		assertInstanceOf(com.joragupra.budinv.domain.IncurredExpense.class, domain.getEntries().get(0));
		assertEquals(75.0, domain.getEntries().get(0).getAmount());
	}
}

package com.joragupra.budinv.dto;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LedgerTransformerTest {

	@Test
	void domainToDto_mapsLedgerPeriodAndAggregates() {
		LocalDate from = LocalDate.of(2024, 1, 1);
		LocalDate to = LocalDate.of(2024, 1, 31);
		com.joragupra.budinv.domain.Ledger domain = new com.joragupra.budinv.domain.Ledger(from, to);

		com.joragupra.budinv.domain.Income income = new com.joragupra.budinv.domain.Income();
		income.setId(1L);
		income.setAmount(500.0);
		income.setIncurredDate(LocalDate.of(2024, 1, 10));
		domain.bookEntry(income);

		com.joragupra.budinv.domain.ExpenseConcept concept = new com.joragupra.budinv.domain.ExpenseConcept("Food");
		com.joragupra.budinv.domain.IncurredExpense expense = new com.joragupra.budinv.domain.IncurredExpense(concept);
		expense.setId(2L);
		expense.setAmount(200.0);
		expense.setIncurredDate(LocalDate.of(2024, 1, 15));
		domain.bookEntry(expense);

		Ledger dto = LedgerTransformer.domainToDto(domain);

		assertEquals(from, dto.getFrom());
		assertEquals(to, dto.getTo());
		assertEquals(500.0, dto.getTotalIncome());
		assertEquals(200.0, dto.getTotalExpense());
		assertEquals(300.0, dto.getBalance());
		assertEquals(2, dto.getEntries().size());
	}

	@Test
	void domainToDto_mapsEntryFieldsAndTypes() {
		LocalDate date = LocalDate.of(2024, 6, 15);
		com.joragupra.budinv.domain.Ledger domain = new com.joragupra.budinv.domain.Ledger(date, date);

		com.joragupra.budinv.domain.Income income = new com.joragupra.budinv.domain.Income();
		income.setId(7L);
		income.setAmount(100.0);
		income.setComments("Salary");
		income.setIncurredDate(date);
		domain.bookEntry(income);

		Ledger dto = LedgerTransformer.domainToDto(domain);
		BookkeepingEntry entry = dto.getEntries().get(0);

		assertInstanceOf(Income.class, entry);
		assertEquals(7L, entry.getId());
		assertEquals(100.0, entry.getAmount());
		assertEquals("Salary", entry.getComments());
		assertEquals(date, entry.getIncurredDate());
		assertEquals("INCOME", entry.getEntryType());
	}

	@Test
	void domainToDto_mapsExpenseEntry() {
		LocalDate date = LocalDate.of(2024, 6, 15);
		com.joragupra.budinv.domain.Ledger domain = new com.joragupra.budinv.domain.Ledger(date, date);

		com.joragupra.budinv.domain.IncurredExpense expense = new com.joragupra.budinv.domain.IncurredExpense(
				new com.joragupra.budinv.domain.ExpenseConcept("Car"));
		expense.setId(3L);
		expense.setAmount(80.0);
		expense.setIncurredDate(date);
		domain.bookEntry(expense);

		Ledger dto = LedgerTransformer.domainToDto(domain);
		BookkeepingEntry entry = dto.getEntries().get(0);

		assertInstanceOf(IncurredExpense.class, entry);
		assertEquals("EXPENSE", entry.getEntryType());
	}

	@Test
	void dtoToDomain_reconstructsLedgerWithEntries() {
		LocalDate from = LocalDate.of(2024, 1, 1);
		LocalDate to = LocalDate.of(2024, 1, 31);

		Ledger dto = new Ledger();
		dto.setFrom(from);
		dto.setTo(to);

		Income incomeDto = new Income();
		incomeDto.setId(1L);
		incomeDto.setAmount(400.0);
		incomeDto.setIncurredDate(LocalDate.of(2024, 1, 10));
		dto.setEntries(List.of(incomeDto));

		com.joragupra.budinv.domain.Ledger domain = LedgerTransformer.dtoToDomain(dto);

		assertEquals(from, domain.getFrom());
		assertEquals(to, domain.getTo());
		assertEquals(1, domain.getEntries().size());
		assertEquals(400.0, domain.getEntries().get(0).getAmount());
		assertInstanceOf(com.joragupra.budinv.domain.Income.class, domain.getEntries().get(0));
	}

	@Test
	void dtoToDomain_nullEntriesDoesNotThrow() {
		Ledger dto = new Ledger();
		dto.setFrom(LocalDate.of(2024, 1, 1));
		dto.setTo(LocalDate.of(2024, 1, 31));
		dto.setEntries(null);

		assertDoesNotThrow(() -> LedgerTransformer.dtoToDomain(dto));
	}

	@Test
	void domainToEvent_mapsIdAndIncurredDate() {
		LocalDate date = LocalDate.of(2024, 6, 15);
		com.joragupra.budinv.domain.Income income = new com.joragupra.budinv.domain.Income();
		income.setId(42L);
		income.setIncurredDate(date);

		Event event = LedgerTransformer.domainToEvent(income);

		assertEquals(42L, event.getId());
		assertEquals(date, event.getStart());
		assertEquals(date, event.getEnd());
	}
}

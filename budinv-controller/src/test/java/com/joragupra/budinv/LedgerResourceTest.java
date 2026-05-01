package com.joragupra.budinv;

import java.lang.reflect.Field;
import java.util.Optional;

import com.joragupra.budinv.dto.Ledger;
import com.joragupra.budinv.repository.LedgerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LedgerResourceTest {

	private LedgerResource resource;

	@BeforeEach
	void setUp() throws Exception {
		resource = new LedgerResource();
		Field repoField = LedgerResource.class.getDeclaredField("repo");
		repoField.setAccessible(true);
		repoField.set(resource, new StubLedgerRepository());
	}

	@Test
	void hello_returnsHelloWorld() {
		assertEquals("Hello world", resource.hello());
	}

	@Test
	void getLedger_returnsLedgerWithEntriesAndAggregates() {
		Ledger ledger = resource.getLedger();

		assertNotNull(ledger);
		assertEquals(3, ledger.getEntries().size());
		assertEquals(300.0, ledger.getTotalIncome());
		assertEquals(30.0, ledger.getTotalExpense());
		assertEquals(270.0, ledger.getBalance());
	}

	private static class StubLedgerRepository implements LedgerRepository {

		@Override
		public Iterable<com.joragupra.budinv.entity.Ledger> findAll() {
			return java.util.List.of();
		}

		@Override
		public <S extends com.joragupra.budinv.entity.Ledger> S save(S entity) {
			return entity;
		}

		@Override
		public <S extends com.joragupra.budinv.entity.Ledger> Iterable<S> saveAll(Iterable<S> entities) {
			return entities;
		}

		@Override
		public Optional<com.joragupra.budinv.entity.Ledger> findById(Long id) {
			return Optional.empty();
		}

		@Override
		public boolean existsById(Long id) {
			return false;
		}

		@Override
		public Iterable<com.joragupra.budinv.entity.Ledger> findAllById(Iterable<Long> ids) {
			return java.util.List.of();
		}

		@Override
		public long count() {
			return 0;
		}

		@Override
		public void deleteById(Long id) {}

		@Override
		public void delete(com.joragupra.budinv.entity.Ledger entity) {}

		@Override
		public void deleteAllById(Iterable<? extends Long> ids) {}

		@Override
		public void deleteAll(Iterable<? extends com.joragupra.budinv.entity.Ledger> entities) {}

		@Override
		public void deleteAll() {}
	}
}

package com.joragupra.budinv;

import java.time.LocalDate;

import com.joragupra.budinv.domain.ExpenseConcept;
import com.joragupra.budinv.domain.Income;
import com.joragupra.budinv.domain.IncurredExpense;
import com.joragupra.budinv.dto.Ledger;
import com.joragupra.budinv.dto.LedgerTransformer;
import com.joragupra.budinv.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ledger")
public class LedgerResource {

	@Autowired
	private LedgerRepository repo;

	@GetMapping("hello")
	public String hello() {
		return "Hello world";
	}

	@GetMapping("/show/list")
	public Ledger getLedger() {
		repo.findAll();

		LocalDate from = LocalDate.now().withDayOfMonth(1);
		com.joragupra.budinv.domain.Ledger ledger = new com.joragupra.budinv.domain.Ledger(from, LocalDate.now());

		Income in1 = new Income();
		in1.setId(1L);
		in1.setAmount(100.0);
		in1.setIncurredDate(LocalDate.now());
		ledger.bookEntry(in1);

		Income in2 = new Income();
		in2.setId(2L);
		in2.setAmount(200.0);
		in2.setIncurredDate(LocalDate.now());
		in2.setComments("This is the second income entry");
		ledger.bookEntry(in2);

		IncurredExpense expense = new IncurredExpense(new ExpenseConcept("Car manteinance"));
		expense.setId(3L);
		expense.setIncurredDate(LocalDate.now());
		expense.setAmount(30.0);
		expense.setComments("This is a expense!");
		ledger.bookEntry(expense);

		return LedgerTransformer.domainToDto(ledger);
	}
}

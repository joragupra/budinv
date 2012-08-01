package com.joragupra.budinv;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.joragupra.budinv.domain.ExpenseConcept;
import com.joragupra.budinv.domain.Income;
import com.joragupra.budinv.domain.IncurredExpense;
import com.joragupra.budinv.dto.Ledger;
import com.joragupra.budinv.dto.LedgerTransformer;

/**
 * Hello world!
 * 
 */
@Path("/ledger")
public class LedgerResource {
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Ledger main() {
		Date init = new Date();
		init.setDate(1);
		com.joragupra.budinv.domain.Ledger ledger = new com.joragupra.budinv.domain.Ledger(init, new Date());
		Income in1 = new Income();
		in1.setAmount(100.0);
		in1.setIncurredDate(new Date());
		ledger.bookEntry(in1);
		Income in2 = new Income();
		in2.setAmount(200.0);
		in2.setIncurredDate(new Date());
		ledger.bookEntry(in2);
		IncurredExpense expense = new IncurredExpense(new ExpenseConcept("Car manteinance"));
		expense.setIncurredDate(new Date());
		expense.setAmount(30.0);
		ledger.bookEntry(expense);
		Ledger result =  LedgerTransformer.domainToDto(ledger);
		return result;
	}
}

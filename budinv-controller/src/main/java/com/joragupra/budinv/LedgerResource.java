package com.joragupra.budinv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.joragupra.budinv.domain.ExpenseConcept;
import com.joragupra.budinv.domain.Income;
import com.joragupra.budinv.domain.IncurredExpense;
import com.joragupra.budinv.dto.Event;
import com.joragupra.budinv.dto.Ledger;
import com.joragupra.budinv.dto.LedgerTransformer;

/**
 * Hello world!
 * 
 */
@Path("/ledger")
public class LedgerResource {
	
	@GET
	@Path("/show/list")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Ledger getLedger() {
		Date init = new Date();
		init.setDate(1);
		com.joragupra.budinv.domain.Ledger ledger = new com.joragupra.budinv.domain.Ledger(init, new Date());
		Income in1 = new Income();
		in1.setId(1L);
		in1.setAmount(100.0);
		in1.setIncurredDate(new Date());
		ledger.bookEntry(in1);
		Income in2 = new Income();
		in2.setId(2L);
		in2.setAmount(200.0);
		in2.setIncurredDate(new Date());
		in2.setComments("This is the second income entry");
		ledger.bookEntry(in2);
		IncurredExpense expense = new IncurredExpense(new ExpenseConcept("Car manteinance"));
		expense.setId(3L);
		expense.setIncurredDate(new Date());
		expense.setAmount(30.0);
		expense.setComments("This is a expense!");
		ledger.bookEntry(expense);
		Ledger result =  LedgerTransformer.domainToDto(ledger);
		return result;
	}
	
	@GET
	@Path("/show/calendar")
	@Produces({MediaType.APPLICATION_JSON})
	public String getLedgerEvents(){
		Date init = new Date();
		init.setDate(1);
		com.joragupra.budinv.domain.Ledger ledger = new com.joragupra.budinv.domain.Ledger(init, new Date());
		Income in1 = new Income();
		in1.setId(1L);
		in1.setAmount(100.0);
		in1.setIncurredDate(new Date());
		ledger.bookEntry(in1);
		Income in2 = new Income();
		in2.setId(2L);
		in2.setAmount(200.0);
		in2.setIncurredDate(new Date());
		in2.setComments("This is the second income entry");
		ledger.bookEntry(in2);
		IncurredExpense expense = new IncurredExpense(new ExpenseConcept("Car manteinance"));
		expense.setId(3L);
		expense.setIncurredDate(new Date());
		expense.setAmount(30.0);
		expense.setComments("This is a expense!");
		Event[] events = new Event[3];
		events[0]=LedgerTransformer.domainToEvent(in1);
		events[1]=LedgerTransformer.domainToEvent(in2);
		events[2]=LedgerTransformer.domainToEvent(expense);
		Gson gson = new Gson();
		return gson.toJson(events);
	}
}

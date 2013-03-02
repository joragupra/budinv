package com.joragupra.budinv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.gordondickens.myapp.repository.ProductRepository;
import com.joragupra.budinv.domain.ExpenseConcept;
import com.joragupra.budinv.domain.Income;
import com.joragupra.budinv.domain.IncurredExpense;
import com.joragupra.budinv.dto.Event;
import com.joragupra.budinv.dto.FormSubmitResponse;
import com.joragupra.budinv.dto.Ledger;
import com.joragupra.budinv.dto.LedgerTransformer;
import com.joragupra.budinv.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Hello world!
 * 
 */
@Controller
@RequestMapping("/ledger")
public class LedgerResource {

    @Autowired
    private LedgerRepository repo;

    @ResponseBody
    @RequestMapping(value = "/show/list", method = RequestMethod.GET)
	public Ledger getLedger() {

        repo.findAll();

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
	
	@POST
	@Path("/save")
	@Consumes("application/x-www-form-urlencoded")
	@Produces({MediaType.APPLICATION_JSON})
	public FormSubmitResponse saveBookkeepingEntry(@FormParam("a") double amount){
		System.out.println("Amount received: " + amount);
		FormSubmitResponse response = new FormSubmitResponse(true, "Success!");
		return response;
	}
}

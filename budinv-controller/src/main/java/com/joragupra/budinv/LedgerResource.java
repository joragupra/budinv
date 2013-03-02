package com.joragupra.budinv;

import com.joragupra.budinv.domain.ExpenseConcept;
import com.joragupra.budinv.domain.Income;
import com.joragupra.budinv.domain.IncurredExpense;
import com.joragupra.budinv.dto.Ledger;
import com.joragupra.budinv.dto.LedgerTransformer;
import com.joragupra.budinv.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Hello world!
 * 
 */
@Controller
@RequestMapping(value = "/ledger")
public class LedgerResource {

    @Autowired
    private LedgerRepository repo;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "Hello world";
    }

    @RequestMapping(value = "/show/list", method = RequestMethod.GET)
    @ResponseBody
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

}

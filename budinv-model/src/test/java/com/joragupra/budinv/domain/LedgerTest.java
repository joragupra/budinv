package com.joragupra.budinv.domain;

import java.util.Date;

import junit.framework.TestCase;

public class LedgerTest extends TestCase {
	
	public void testCalculateIncome(){
		Date today = new Date();
		Date tomorrow = new Date();
		System.out.println("Today: " + today + ". Tomorrow: " + tomorrow);
		Ledger ledger = new Ledger();
		Income todayIncome = new Income();
		todayIncome.setAmount(1000.00);
		todayIncome.setIncurredDate(today);
		ledger.bookEntry(todayIncome);
		Income tomorrowIncome = new Income();
		tomorrowIncome.setAmount(1000.00);
		tomorrowIncome.setIncurredDate(tomorrow);
		ledger.bookEntry(tomorrowIncome);
		assertEquals(2000.00, ledger.calculateIncome(today, tomorrow));
	}
	
	public void testCalculateExpense(){
		Date today = new Date();
		Date tomorrow = new Date();
		System.out.println("Today: " + today + ". Tomorrow: " + tomorrow);
		Ledger ledger = new Ledger();
		IncurredExpense todayExpense = new IncurredExpense(new ExpenseConcept("CAR"));
		todayExpense.setAmount(1000.00);
		todayExpense.setIncurredDate(today);
		ledger.bookEntry(todayExpense);
		IncurredExpense tomorrowExpense = new IncurredExpense(new ExpenseConcept("FOOD"));
		tomorrowExpense.setAmount(1000.00);
		tomorrowExpense.setIncurredDate(tomorrow);
		ledger.bookEntry(tomorrowExpense);
		assertEquals(2000.00, ledger.calculateExpense(today, tomorrow));
	}
	
	public void testCalculateBalanceExpense(){
		Date today = new Date();
		Date tomorrow = new Date();
		tomorrow.setDate(tomorrow.getDate() + 1);
		Ledger ledger = new Ledger();
		Income todayIncome = new Income();
		todayIncome.setAmount(1000.00);
		todayIncome.setIncurredDate(today);
		ledger.bookEntry(todayIncome);
		IncurredExpense tomorrowExpense = new IncurredExpense(new ExpenseConcept("FOOD"));
		tomorrowExpense.setAmount(600.00);
		tomorrowExpense.setIncurredDate(tomorrow);
		ledger.bookEntry(tomorrowExpense);
		assertEquals(400.00, ledger.calculateBalance(today, tomorrow));
	}
}

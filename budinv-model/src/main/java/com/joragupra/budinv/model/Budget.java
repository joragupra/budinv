package com.joragupra.budinv.model;

import java.util.Date;
import java.util.List;

/**
 * @author: jagudo
 * @since: 3/03/13
 */
public class Budget {

    private BudgetPeriod applicationPeriod;

    private Date validFrom;

    private Date validTo;

    private List<BudgetLine> budgetedLines;

    public Budget(Date from, Date to) {
        applicationPeriod = BudgetPeriod.MONTHLY;
        this.validFrom = from;
        this.validTo = to;
    }

    public Double totalBudgeted() {
        Double total = 0.0;
        for(BudgetLine line : budgetedLines) {
            total += line.total();
        }
        return total;
    }

    public static enum BudgetPeriod {

        MONTHLY
    }
}

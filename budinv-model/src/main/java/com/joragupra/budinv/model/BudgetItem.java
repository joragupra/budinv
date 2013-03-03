package com.joragupra.budinv.model;

/**
 * @author: jagudo
 * @since: 3/03/13
 */
public class BudgetItem {

    private Category category;

    private Double budgetedAmount;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getBudgetedAmount() {
        return budgetedAmount;
    }

    public void setBudgetedAmount(Double budgetedAmount) {
        this.budgetedAmount = budgetedAmount;
    }
}

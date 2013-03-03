package com.joragupra.budinv.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jagudo
 * @since: 3/03/13
 */
public class BudgetLine {

    private ExpenseType type;

    private List<BudgetItem> budgetedItems;

    BudgetLine(ExpenseType type) {
        this.type = type;
        budgetedItems = new ArrayList<BudgetItem>();
    }

    void addItem(BudgetItem item) {
        this.budgetedItems.add(item);
    }

    void removeItem(Category category) {
        for(BudgetItem item : budgetedItems) {
            if(item.getCategory().equals(category)) {
                this.budgetedItems.remove(item);
                return;
            }
        }
    }

    boolean cotainsCategory(Category category) {
        for(BudgetItem item : budgetedItems) {
            if(item.getCategory().equals(category)) {
                return true;
            }
        }
        return false;
    }

    public String lineTitle() {
        return this.type.getName();
    }

    public Double total() {
        Double total = 0.0;
        for(BudgetItem item : budgetedItems) {
            total += item.getBudgetedAmount();
        }
        return total;
    }
}

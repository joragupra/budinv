package com.joragupra.budinv.model;

import java.util.Date;

/**
 * @author: jagudo
 * @since: 3/03/13
 */
public abstract class BookkeepingEntry {

    private Long id;

    private Date date;

    private Double amount;

    private String title;

    private String comment;

    private Category category;

    public BookkeepingEntry(Date date, Double amount, String title) {
        this(date, amount, title, null);
    }

    public BookkeepingEntry(Date date, Double amount, String title, Category category) {
        this.date = date;
        this.amount = amount;
        this.title = title;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

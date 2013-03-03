package com.joragupra.budinv.model;

/**
 * @author: jagudo
 * @since: 3/03/13
 */
public class ExpenseType {

    private Long id;

    private String code;

    private String name;

    private String comments;

    public ExpenseType(String code, String name) {
        this(code, name, null);
    }

    public ExpenseType(String code, String name, String comments) {
        this.code = code;
        this.name = name;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getComments() {
        return comments;
    }
}

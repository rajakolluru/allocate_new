package com.vymo.collectiq.allocation.model;

import org.springframework.expression.Expression;

import java.util.List;

public class Rule {
    public static final  String DEFAULT_ALLOCATEE_TYPE = "user";
    public int priority = 1;
    public Expression parsedUserExpression;
    public Expression parsedAllocatableEntityExpression;
    private String id;
    private List<String> attributes;
    public String allocateeType = DEFAULT_ALLOCATEE_TYPE;

    public String getAllocationType() {
        return allocationType;
    }

    public void setAllocationType(String allocationType) {
        this.allocationType = allocationType;
    }

    private String allocationType;

    public String getUserExpression() {
        return userExpression;
    }

    public void setUserExpression(String expression) {
        this.userExpression = expression;
    }

    private String userExpression; // OGNL expression to filter the users.

    public String getAllocatableEntityExpression() {
        return allocatableEntityExpression;
    }

    public void setAllocatableEntityExpression(String allocatableEntityExpression) {
        this.allocatableEntityExpression = allocatableEntityExpression;
    }

    private String allocatableEntityExpression; // Expression to filter out from allocatableEntity
    public Rule() {}

    public Rule(String id, List<String> attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public List<String> getAttributes() { return attributes; }
    public void setAttributes(List<String> attributes) { this.attributes = attributes; }

    @Override
    public String toString() {
        return String.format("Rule{id='%s', attributes=%s}", id, attributes);
    }
}
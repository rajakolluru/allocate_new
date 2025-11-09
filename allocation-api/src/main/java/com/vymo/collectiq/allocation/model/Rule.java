package com.vymo.collectiq.allocation.model;

import org.springframework.expression.Expression;

import java.util.List;

public class Rule {
    public Expression parsedExpression;
    private String id;
    private List<String> attributes;

    public String getAllocationType() {
        return allocationType;
    }

    public void setAllocationType(String allocationType) {
        this.allocationType = allocationType;
    }

    private String allocationType;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    private String expression; // OGNL expression to filter the users.

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
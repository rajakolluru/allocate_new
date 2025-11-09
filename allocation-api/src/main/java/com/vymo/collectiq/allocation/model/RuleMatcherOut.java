package com.vymo.collectiq.allocation.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleMatcherOut {
    public Rule  rule;
    public List<User> users = new ArrayList<>();
    public Map<String,Object> metadata = new HashMap<>();

    @Override
    public String toString() {
        return "RuleMatcherOut{" +
                "ruleMatched = " + rule +
                ", #users = " + users.size() +
                '}';
    }
}

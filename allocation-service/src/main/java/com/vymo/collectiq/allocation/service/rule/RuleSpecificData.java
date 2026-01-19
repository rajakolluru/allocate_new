package com.vymo.collectiq.allocation.service.rule;

import com.vymo.collectiq.allocation.model.Allocatee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleSpecificData {
    public RuleSpecificData(){}
    public RuleSpecificData(List<Allocatee> allocatees){ this.allocatees = allocatees;}
    public List<Allocatee> allocatees;
    public Map<String,Object> meta = new HashMap<>();
}

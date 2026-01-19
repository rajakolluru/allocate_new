package com.vymo.collectiq.allocation.service.allocation;

import com.vymo.collectiq.allocation.model.Allocatee;
import com.vymo.collectiq.allocation.model.RuleMatcherOut;

import java.util.Map;

public interface AllocationStrategy {
    public Allocatee allocate(RuleMatcherOut ruleMatcherOut, Map<String,String> allocatableEntity);
}

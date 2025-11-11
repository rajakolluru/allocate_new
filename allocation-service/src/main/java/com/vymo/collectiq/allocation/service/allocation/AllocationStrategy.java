package com.vymo.collectiq.allocation.service.allocation;

import com.vymo.collectiq.allocation.model.RuleMatcherOut;
import com.vymo.collectiq.allocation.model.User;

import java.util.List;
import java.util.Map;

public interface AllocationStrategy {
    public User allocate(RuleMatcherOut ruleMatcherOut, Map<String,String> allocatableEntity);
}

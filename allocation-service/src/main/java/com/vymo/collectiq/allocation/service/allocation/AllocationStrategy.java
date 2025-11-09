package com.vymo.collectiq.allocation.service.allocation;

import com.vymo.collectiq.allocation.model.RuleMatcherOut;
import com.vymo.collectiq.allocation.model.User;

import java.util.List;

public interface AllocationStrategy {
    public User allocate(RuleMatcherOut ruleMatcherOut);
}

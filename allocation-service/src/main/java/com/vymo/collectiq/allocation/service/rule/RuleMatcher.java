package com.vymo.collectiq.allocation.service.rule;

import com.vymo.collectiq.allocation.dto.AllocationInput;
import com.vymo.collectiq.allocation.model.RuleMatcherOut;

public interface RuleMatcher {
    public RuleMatcherOut filterUsersByRules(AllocationInput input);
}

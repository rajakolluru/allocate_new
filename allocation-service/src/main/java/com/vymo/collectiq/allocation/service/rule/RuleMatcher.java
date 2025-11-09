package com.vymo.collectiq.allocation.service.rule;

import com.vymo.collectiq.allocation.dto.AllocationInput;
import com.vymo.collectiq.allocation.model.RuleMatcherOut;
import com.vymo.collectiq.allocation.model.User;

import java.util.List;
import java.util.Map;

public interface RuleMatcher {
    public RuleMatcherOut filterUsersByRules(AllocationInput input);
}

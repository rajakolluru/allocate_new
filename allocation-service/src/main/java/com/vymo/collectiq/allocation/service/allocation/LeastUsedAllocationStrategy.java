package com.vymo.collectiq.allocation.service.allocation;

import com.vymo.collectiq.allocation.model.Allocatee;
import com.vymo.collectiq.allocation.model.RuleMatcherOut;
import com.vymo.collectiq.allocation.service.allocation.counts.AllocationCountTracker;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Of all the qualifying users, return the one that is least used.
 */
public class LeastUsedAllocationStrategy implements AllocationStrategy{
    @Autowired
    AllocationCountTracker allocationCache;

    @Override
    public Allocatee allocate(RuleMatcherOut ruleMatcherOut, Map<String,String> allocatableEntity) {
        List<Allocatee> allocatees = ruleMatcherOut.allocatees;
        if (allocatees.size() == 1) return allocatees.get(0);
        else if (allocatees.isEmpty()) return null;
        String allocateeType = ruleMatcherOut.rule.allocateeType;
        allocatees = allocationCache.sortByIncreasingAllocationCount(allocateeType,allocatees);
        return allocatees.get(0);
    }
}

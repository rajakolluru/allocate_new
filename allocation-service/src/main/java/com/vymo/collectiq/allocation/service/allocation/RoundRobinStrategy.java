package com.vymo.collectiq.allocation.service.allocation;

import com.vymo.collectiq.allocation.model.Allocatee;
import com.vymo.collectiq.allocation.model.RuleMatcherOut;

import java.util.List;
import java.util.Map;

/**
 * Store the last allocated index in the meta data. Use that to circle through the contending
 * users.
 */
public class RoundRobinStrategy implements AllocationStrategy{
    @Override
    public Allocatee allocate(RuleMatcherOut ruleMatcherOut, Map<String,String> allocatableEntity) {
        List<Allocatee> allocatees = ruleMatcherOut.allocatees;
        if (allocatees == null || allocatees.isEmpty()){
            return null;
        }
        int index = (int)ruleMatcherOut.metadata.computeIfAbsent("lastAllocatedIndex",
                   k -> -1);
        if (++index >= allocatees.size() )
            index = 0;
        ruleMatcherOut.metadata.put("lastAllocatedIndex",index);
        return allocatees.get(index);
    }
}
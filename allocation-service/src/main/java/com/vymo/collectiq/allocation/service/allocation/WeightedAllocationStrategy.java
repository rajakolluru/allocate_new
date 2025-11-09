package com.vymo.collectiq.allocation.service.allocation;

import com.vymo.collectiq.allocation.model.RuleMatcherOut;
import com.vymo.collectiq.allocation.model.User;

import java.util.List;

public class WeightedAllocationStrategy implements AllocationStrategy{
    @Override
    public User allocate(RuleMatcherOut ruleMatcherOut) {
        List<User> users = ruleMatcherOut.users;
        if (users == null || users.isEmpty()){
            return null;
        }
        int index = (int)ruleMatcherOut.metadata.computeIfAbsent("lastAllocatedIndex",
                k -> -1);
        if (++index >= users.size() )
            index = 0;
        ruleMatcherOut.metadata.put("lastAllocatedIndex",index);
        return users.get(index);
    }
}

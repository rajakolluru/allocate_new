package com.vymo.collectiq.allocation.service.allocation;

import com.vymo.collectiq.allocation.model.RuleMatcherOut;
import com.vymo.collectiq.allocation.model.User;
import com.vymo.collectiq.allocation.service.allocation.counts.AllocationCache;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Of all the qualifying users, return the one that is least used.
 */
public class LeastUsedAllocationStrategy implements AllocationStrategy{
    @Autowired AllocationCache allocationCache;
    @Override
    public User allocate(RuleMatcherOut ruleMatcherOut) {
        List<User> users = ruleMatcherOut.users;
        Map<User,Integer> intersectingKeys = allocationCache.sortedIntersection(users);
        // System.out.println("Intersection keys = " + intersectingKeys);
        Optional<User> o = intersectingKeys.keySet().stream().findFirst();
        return o.orElse(null);
    }
}

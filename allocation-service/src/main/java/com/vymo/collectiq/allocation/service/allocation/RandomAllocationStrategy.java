package com.vymo.collectiq.allocation.service.allocation;

import com.vymo.collectiq.allocation.model.Allocatee;
import com.vymo.collectiq.allocation.model.RuleMatcherOut;

import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;

public class RandomAllocationStrategy implements AllocationStrategy{
    RandomGenerator randomGenerator = RandomGenerator.getDefault();
    @Override
    public Allocatee allocate(RuleMatcherOut ruleMatcherOut, Map<String,String> allocatableEntity) {
        List<Allocatee> allocatees = ruleMatcherOut.allocatees;
        if (allocatees == null || allocatees.isEmpty()) return null;
        // System.out.println("users is " + users);
        int selected = randomGenerator.nextInt(allocatees.size());
        return allocatees.get(selected);
    }
}

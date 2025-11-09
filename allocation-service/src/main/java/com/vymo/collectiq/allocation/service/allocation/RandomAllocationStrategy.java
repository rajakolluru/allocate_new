package com.vymo.collectiq.allocation.service.allocation;

import com.vymo.collectiq.allocation.model.RuleMatcherOut;
import com.vymo.collectiq.allocation.model.User;

import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;

public class RandomAllocationStrategy implements AllocationStrategy{
    RandomGenerator randomGenerator = RandomGenerator.getDefault();
    @Override
    public User allocate(RuleMatcherOut ruleMatcherOut) {
        List<User> users = ruleMatcherOut.users;
        if (users == null || users.isEmpty()) return null;
        // System.out.println("users is " + users);
        int selected = randomGenerator.nextInt(users.size());
        return users.get(selected);
    }
}

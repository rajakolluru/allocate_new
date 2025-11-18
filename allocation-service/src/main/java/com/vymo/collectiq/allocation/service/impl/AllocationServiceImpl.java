package com.vymo.collectiq.allocation.service.impl;

import com.vymo.collectiq.allocation.dto.AllocationInput;
import com.vymo.collectiq.allocation.model.RuleMatcherOut;
import com.vymo.collectiq.allocation.model.User;
import com.vymo.collectiq.allocation.service.AllocationService;
import com.vymo.collectiq.allocation.service.allocation.AgencyAllocationWeightages;
import com.vymo.collectiq.allocation.service.allocation.AllocationStrategy;
import com.vymo.collectiq.allocation.service.allocation.AllocationStrategyFactory;
import com.vymo.collectiq.allocation.service.check.ThresholdChecker;
import com.vymo.collectiq.allocation.service.rule.RuleSpecificHashCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class AllocationServiceImpl implements AllocationService{
    private static final Logger logger = LoggerFactory.getLogger(AllocationServiceImpl.class);

    @Autowired private RuleSpecificHashCache ruleSpecificHashCache;
    @Autowired private AllocationStrategyFactory allocationStrategyFactory;
    @Autowired private ThresholdChecker thresholdChecker;
    @Autowired private AgencyAllocationWeightages agencyAllocationWeightages;

    public User doAllocation(AllocationInput input){
        Map<String, String> allocatableEntity = input.allocatableEntity;
        RuleMatcherOut ruleMatcherOut = ruleSpecificHashCache.filterUsersByRules(input);
        List<User> matchedUsers = ruleMatcherOut.users;
        if (matchedUsers == null || matchedUsers.isEmpty()){
            logger.warn("No matched users found for " + allocatableEntity);
            return null;
        }
        // Allocate one of the users to the allocatable entity.
        User allocatedUser = null;
        do {
            // Remove the allocatedUser from future considerations in case threshold check failed.
            if(allocatedUser != null){
                matchedUsers.remove(allocatedUser);
            }
            AllocationStrategy allocationStrategy;
            if (input.allocationStrategy != null && !input.allocationStrategy.isEmpty())
                allocationStrategy = allocationStrategyFactory.obtainAllocationStrategy(input.allocationStrategy);
            else
                allocationStrategy = allocationStrategyFactory.obtainAllocationStrategy();
            allocatedUser = allocationStrategy.allocate(ruleMatcherOut,input.allocatableEntity);
            if (allocatedUser == null) return null;
        }while(!thresholdChecker.eligible(allocatedUser));
        return allocatedUser;
    }

    public void assignWeightages (String agency, Map<String,Integer> weightages){
        agencyAllocationWeightages.prep(agency,weightages);
    }
}
package com.vymo.collectiq.allocation.service.allocation;

import com.vymo.collectiq.allocation.model.RuleMatcherOut;
import com.vymo.collectiq.allocation.model.User;
import com.vymo.collectiq.allocation.service.rule.RuleSpecificHashCache;
import com.vymo.collectiq.allocation.service.users.UsersDB;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class WeightedAllocationStrategy implements AllocationStrategy{
    @Autowired UsersDB usersDB;
    @Autowired RuleSpecificHashCache ruleSpecificHashCache;
    @Autowired AgencyAllocationWeightages allocationWeightages;
    @Override
    public User allocate(RuleMatcherOut ruleMatcherOut, Map<String,String> allocatableEntity) {
        List<User> users = ruleMatcherOut.users;
        if (users == null || users.isEmpty()){
            return null;
        }
        int index = (int)ruleMatcherOut.metadata.computeIfAbsent("lastAllocatedIndex",
                k -> -1);
        String[] allocationMatrix = (String[])ruleMatcherOut.metadata.get("allocationMatrix");
        if (allocationMatrix == null){
            // for this agency see if there is an allocation matrix in the Agency Weightages
            String agency = allocatableEntity.get("entity");
            allocationMatrix = allocationWeightages.obtainAllocationMatrix(agency);
            if (allocationMatrix == null)
                return null;
            ruleMatcherOut.metadata.put("allocationMatrix",allocationMatrix);
        }
        if (++index >= allocationMatrix.length )
            index = 0;
        ruleMatcherOut.metadata.put("lastAllocatedIndex",index);
        return usersDB.userMap.get(allocationMatrix[index]);
    }

}
package com.vymo.collectiq.allocation.service.allocation;

import com.vymo.collectiq.allocation.model.RuleMatcherOut;
import com.vymo.collectiq.allocation.model.Allocatee;
import com.vymo.collectiq.allocation.service.rule.RuleSpecificHashCache;
import com.vymo.collectiq.allocation.service.source.UserSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class WeightedAllocationStrategy implements AllocationStrategy{
    @Autowired RuleSpecificHashCache ruleSpecificHashCache;
    @Autowired AgencyAllocationWeightages allocationWeightages;
    @Autowired
    UserSource userSource;
    @Override
    public Allocatee allocate(RuleMatcherOut ruleMatcherOut, Map<String,String> allocatableEntity) {
        List<Allocatee> allocatees = ruleMatcherOut.allocatees;
        if (allocatees == null || allocatees.isEmpty()){
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
        return userSource.getAllocateeById(allocationMatrix[index]);
    }

}
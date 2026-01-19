package com.vymo.collectiq.allocation.service.twolevel;

import com.vymo.collectiq.allocation.dto.AllocationInput;
import com.vymo.collectiq.allocation.model.Allocatee;
import com.vymo.collectiq.allocation.service.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Implements Two Level Allocation. <br/>
 * In the first level, cases are allocated to an agency. In the next level, the agents are allocated
 * to the case.
 */
public class TwoLevelAllocation {
    @Autowired
    AllocationService allocationService;
    public static final String FIRST_LEVEL = "agency";
    public static final String FIRST_LEVEL_KEY = "entity";
    public static final String SECOND_LEVEL = "agency-to-agent";
    public static final String SECOND_LEVEL_KEY = "agent";
    public void doTwoLevelAllocation(List<Map<String,String>> cases){
        for (Map<String,String> _case: cases){
           doTwoLevelAllocation(_case);
        }
    }

    public void doTwoLevelAllocation(Map<String,String> _case){
        doFirstLevelAllocation(_case);
        doSecondLevelAllocation(_case);
    }

    public void doFirstLevelAllocation(Map<String,String> _case){
        doAllocation(_case,FIRST_LEVEL,FIRST_LEVEL_KEY);
    }

    public void doSecondLevelAllocation(Map<String,String> _case){
        doAllocation(_case,SECOND_LEVEL,SECOND_LEVEL_KEY);
    }

    private void doAllocation(Map<String,String> _case, String allocationType,
                               String keyName){
        AllocationInput allocationInput = new AllocationInput();
        allocationInput.allocatableEntity = _case;
        allocationInput.allocationType = allocationType;
        Allocatee allocatee = allocationService.doAllocation(allocationInput);
        _case.put(keyName,allocatee.getId());
    }

}

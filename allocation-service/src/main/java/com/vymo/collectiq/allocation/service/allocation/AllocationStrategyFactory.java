package com.vymo.collectiq.allocation.service.allocation;

import com.vymo.collectiq.allocation.service.config.ConfigServer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class AllocationStrategyFactory {
    @Autowired ConfigServer configServer;
    private final Map<String,AllocationStrategy> strategies = new HashMap<>();

    public AllocationStrategy obtainAllocationStrategy(String strategy){
        return  strategies.get(strategy);
    }

    public AllocationStrategy obtainDefaultAllocationStrategy(String allocationType){
        String chosenAllocationStrategy = configServer.getStrategy(allocationType);
        return  strategies.get(chosenAllocationStrategy);
    }

    public void registerAllocationStrategy(String id, AllocationStrategy allocationStrategy){
        strategies.put(id,allocationStrategy);
    }
}

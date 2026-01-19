package com.vymo.collectiq.allocation.service.check;

import com.vymo.collectiq.allocation.model.Allocatee;
import com.vymo.collectiq.allocation.service.allocation.counts.AllocationCountTracker;
import org.springframework.beans.factory.annotation.Autowired;

public class ThresholdCheckerImpl implements ThresholdChecker{
    @Autowired
    AllocationCountTracker allocationCache;
    @Override
    public boolean eligible(String allocateeType,Allocatee allocatee) {
        return  allocationCache.incrementCount(allocateeType,allocatee);
    }
}

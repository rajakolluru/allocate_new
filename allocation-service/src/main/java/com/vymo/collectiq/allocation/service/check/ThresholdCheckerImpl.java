package com.vymo.collectiq.allocation.service.check;

import com.vymo.collectiq.allocation.model.User;
import com.vymo.collectiq.allocation.service.allocation.counts.AllocationCache;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class ThresholdCheckerImpl implements ThresholdChecker{
    @Autowired AllocationCache allocationCache;
    @Override
    public boolean eligible(User user) {
        return  allocationCache.incrementCount(user);
    }
}

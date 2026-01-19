package com.vymo.collectiq.allocation.service.check;

import com.vymo.collectiq.allocation.model.Allocatee;

public interface ThresholdChecker {
    public boolean eligible(String allocateeType, Allocatee allocatee);
}

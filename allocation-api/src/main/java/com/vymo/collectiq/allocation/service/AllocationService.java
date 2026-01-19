package com.vymo.collectiq.allocation.service;

import com.vymo.collectiq.allocation.dto.AllocationInput;
import com.vymo.collectiq.allocation.model.Allocatee;

public interface AllocationService {
    Allocatee doAllocation(AllocationInput input);
}

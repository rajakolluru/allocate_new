package com.vymo.collectiq.allocation.service;

import com.vymo.collectiq.allocation.dto.AllocationInput;
import com.vymo.collectiq.allocation.model.User;

import java.util.Map;

public interface AllocationService {
    User doAllocation(AllocationInput input);
}

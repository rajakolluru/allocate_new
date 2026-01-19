package com.vymo.collectiq.allocation.service.source;

import com.vymo.collectiq.allocation.model.Allocatee;

import java.util.List;

public interface AllocateeSource {
    public List<Allocatee> getAllAllocatees();
    public String getAllocateeType();

    Allocatee getAllocateeById(String id);
}

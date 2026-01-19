package com.vymo.collectiq.allocation.service.source;

import com.vymo.collectiq.allocation.model.Allocatee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseAllocateeSource implements AllocateeSource{
    public Map<String, Allocatee> allocateesMap = new HashMap<>();
    protected List<Allocatee> allocatees;

    public BaseAllocateeSource(List<Allocatee> allocatees) {
        this.allocatees = allocatees;
        this.allocateesMap = this.allocatees.stream().collect(Collectors.groupingBy(Allocatee::getId,
                Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> list.get(0) // Get the first element from the list
                )));
    }

    @Override
    public List<Allocatee> getAllAllocatees() {
        return allocatees;
    }

    @Override
    public Allocatee getAllocateeById(String id) {
        return allocateesMap.get(id);
    }
}

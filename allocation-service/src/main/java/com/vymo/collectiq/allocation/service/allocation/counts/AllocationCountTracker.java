package com.vymo.collectiq.allocation.service.allocation.counts;

import com.vymo.collectiq.allocation.model.Allocatee;
import com.vymo.collectiq.allocation.service.config.ConfigServer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This keeps track of all Allocatees and their allocation counts. It enforces threshold policies.<br/>
 * The chief data structure keeps track of the counts for each Allocatee. All Allocatees who belong
 * to one Allocatee type are kept together. Hence, the structure is:<br/>
 * Allocatee type -> Map of allocatee ID and their count.
 */
public class AllocationCountTracker {
    private final Map<String,Map<String, Integer>> counts = new LinkedHashMap<>();
    @Autowired ConfigServer configServer;
    public void load(String allocateeType,List<Allocatee> allocatees){
        Map<String,Integer> allocateeMap =  allocatees.stream().collect(Collectors.toMap(Allocatee::getId, (u)->0,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        counts.put(allocateeType,allocateeMap);
    }
    public AllocationCountTracker(){}
    public boolean incrementCount(String allocateeType,Allocatee u){
        Map<String,Integer> allocateeMap = counts.get(allocateeType);
        if (allocateeMap == null) return false;
        int count = allocateeMap.computeIfAbsent(u.getId(),(u1)-> 0);
        int threshold = configServer.getThreshold(allocateeType);
        if ((count + 1) > threshold){
            return false;
        }else {
            allocateeMap.put(u.getId(),++count);
            return true;
        }
    }

    public int count(String allocateeType, Allocatee allocatee){
        Map<String,Integer> allocateeMap = counts.get(allocateeType);
        if (allocateeMap == null) return 0;
        return allocateeMap.get(allocatee.getId());
    }

    /**
     *
     * @param allocatees the list to count
     * @return a sorted list of allocatees in increasing order of their allocation count
     */
    public List<Allocatee> sortByIncreasingAllocationCount(String allocateeType,List<Allocatee> allocatees){
        Map<String,Integer> allocateeMap = counts.get(allocateeType);
        if (allocateeMap == null) return allocatees;
       allocatees.sort(Comparator.comparing(k-> allocateeMap.get(k.getId())));
       return allocatees;
    }
}

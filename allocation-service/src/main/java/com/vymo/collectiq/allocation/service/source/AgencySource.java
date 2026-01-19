package com.vymo.collectiq.allocation.service.source;

import com.vymo.collectiq.allocation.model.Allocatee;

import java.util.List;

public class AgencySource extends BaseAllocateeSource{
    public final static String AGENT = "agent";
    public AgencySource(List<Allocatee> allocatees){
        super(allocatees);
    }

    @Override
    public String getAllocateeType() {
        return AGENT;
    }
}

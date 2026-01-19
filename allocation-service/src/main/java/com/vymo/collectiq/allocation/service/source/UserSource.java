package com.vymo.collectiq.allocation.service.source;

import com.vymo.collectiq.allocation.model.Allocatee;
import com.vymo.collectiq.allocation.model.Rule;

import java.util.List;

public class UserSource extends BaseAllocateeSource {
    public final static String USER = Rule.DEFAULT_ALLOCATEE_TYPE;
    public UserSource(List<Allocatee> allocatees){
        super(allocatees);
    }

    @Override
    public String getAllocateeType() {
        return USER;
    }
}

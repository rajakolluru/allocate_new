package com.vymo.collectiq.allocation.dto;

import java.util.Map;

public class AllocationInput {
    public static final String DEFAULT_ALLOCATION_TYPE = "default";
    public Map<String,String> allocatableEntity;
    public String allocationType = DEFAULT_ALLOCATION_TYPE;
}

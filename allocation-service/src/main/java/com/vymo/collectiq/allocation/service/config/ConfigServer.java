package com.vymo.collectiq.allocation.service.config;

public interface ConfigServer {
    public String getStrategy(String allocationType);

    public int getThreshold(String allocateeType);
}

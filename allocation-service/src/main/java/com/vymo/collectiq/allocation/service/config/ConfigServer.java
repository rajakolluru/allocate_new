package com.vymo.collectiq.allocation.service.config;

public interface ConfigServer {
    public String getStrategy();

    public int getThreshold(String allocateeType);
}

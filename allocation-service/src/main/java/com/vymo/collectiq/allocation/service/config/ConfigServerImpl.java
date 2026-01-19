package com.vymo.collectiq.allocation.service.config;

import java.util.Map;

public class ConfigServerImpl implements ConfigServer{
    public AllocationConfig getConfig() {
        return config;
    }
    public void setConfig(AllocationConfig c) {
        this.config = c;
    }

    private AllocationConfig config;

    public String getStrategy() {
        return config.strategy;
    }

    public int getThreshold(String allocateeType){
        return config.threshold.computeIfAbsent(allocateeType,a->0);
    }

    public static class AllocationConfig {
        public String getStrategy() {
            return strategy;
        }

        public void setStrategy(String strategy) {
            this.strategy = strategy;
        }

        public String strategy;

        public Map<String, Integer> getThreshold() {
            return threshold;
        }

        public void setThreshold(Map<String, Integer> threshold) {
            this.threshold = threshold;
        }

        public Map<String,Integer> threshold;
    }
}

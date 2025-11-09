package com.vymo.collectiq.allocation.service.config;

import java.util.Map;

public class ConfigServerImpl implements ConfigServer{
    public Map<String, String> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, String> configs) {
        this.configs = configs;
    }

    private  Map<String,String> configs;
    @Override
    public String getConfig(String configName) {
        // System.out.println("Config map is " + configs);
        return configs.get(configName);
    }
}

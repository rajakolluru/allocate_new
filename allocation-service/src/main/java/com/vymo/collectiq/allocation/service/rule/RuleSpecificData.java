package com.vymo.collectiq.allocation.service.rule;

import com.vymo.collectiq.allocation.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleSpecificData {
    public RuleSpecificData(){}
    public RuleSpecificData(List<User> users){ this.users = users;}
    public List<User> users;
    public Map<String,Object> meta = new HashMap<>();
}

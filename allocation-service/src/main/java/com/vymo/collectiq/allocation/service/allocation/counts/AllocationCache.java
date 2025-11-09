package com.vymo.collectiq.allocation.service.allocation.counts;

import com.vymo.collectiq.allocation.model.User;
import com.vymo.collectiq.allocation.service.config.ConfigServer;
import com.vymo.collectiq.allocation.service.rule.UserCache;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AllocationCache {
    Map<User, Integer> userThresholds ;
    @Autowired ConfigServer configServer;
    public AllocationCache(UserCache userCache){
        userThresholds = userCache.users.stream().collect(Collectors.toMap((u)->u, (u)->0,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
    public boolean incrementCount(User u){
        int count = userThresholds.computeIfAbsent(u,(u1)-> 0);
        int threshold =  Integer.parseInt(configServer.getConfig("threshold"));
        if ((count + 1) > threshold){
            return false;
        }else {
            userThresholds.put(u,++count);
            return true;
        }
    }

    public int count(User user){
        return userThresholds.get(user);
    }

    public Map<User,Integer> sortedIntersection(List<User> users){
        // System.out.println("Users is " + users);
        return userThresholds.entrySet().stream()
                .filter(entry -> users.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

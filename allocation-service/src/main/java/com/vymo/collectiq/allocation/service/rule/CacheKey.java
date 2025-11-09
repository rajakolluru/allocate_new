package com.vymo.collectiq.allocation.service.rule;

import com.vymo.collectiq.allocation.model.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CacheKey {
    /**
     * This method is almost identical to the constructor but can return null if the values are
     * not filled.
     * @param rule the rule that determines this cache key
     * @param user the user passed as a map
     * @return the CacheKey that has the attributes specified in the rule.
     */
    public static CacheKey constructCacheKey(Rule rule, Map<String,String> user){
        CacheKey cacheKey = new CacheKey();
        cacheKey.keys = new ArrayList<>();
        for(String attribute: rule.getAttributes()){
            if (!user.containsKey(attribute)) return null;
            cacheKey.keys.add(user.get(attribute));
        }
        return cacheKey;
    }

    private CacheKey(){}

    public CacheKey(Rule rule,Map<String,String> user){
        this.keys = new ArrayList<>();
        for(String attribute: rule.getAttributes()){
            this.keys.add(user.get(attribute));
        }
    }
    List<String> keys ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CacheKey cacheKey)) return false;
        for (int i = 0; i < keys.size(); i++){
            Object v = cacheKey.keys.get(i);
            if ( (v == null && keys.get(i) != null ) ||
                (v != null && !v.equals(keys.get(i)) ) )
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(keys.toArray());
    }

}

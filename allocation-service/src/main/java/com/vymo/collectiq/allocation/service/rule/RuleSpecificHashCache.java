package com.vymo.collectiq.allocation.service.rule;

import com.vymo.collectiq.allocation.dto.AllocationInput;
import com.vymo.collectiq.allocation.model.Rule;
import com.vymo.collectiq.allocation.model.RuleMatcherOut;
import com.vymo.collectiq.allocation.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vymo.collectiq.allocation.configuration.dao.LoaderUtil.loadRules;

public class RuleSpecificHashCache implements RuleMatcher{
    private static final Logger logger = LoggerFactory.getLogger(RuleSpecificHashCache.class);
    private final UserCache userCache;
    private final List<Rule> availableRules;
    private final Map<String, Map<CacheKey, RukeSpecificData>> ruleCaches = new HashMap<>();

    public RuleSpecificHashCache(UserCache userCache) throws Exception {
        this.userCache = userCache;
        this.availableRules = loadRules();
        long startTime = System.nanoTime();
        constructCache();
        long endTime = System.nanoTime();
        logger.info("RuleSpecificHashCache: Time to construct the cache: " + (endTime - startTime)/1000 + " micros");
    }

    private final ExpressionParser expressionParser = new SpelExpressionParser();
    public boolean match(Rule rule, Map<String,String> user){
        String expression = rule.getExpression();
        if(expression == null || expression.isEmpty()) return true;
        if (rule.parsedExpression == null)
            rule.parsedExpression = expressionParser.parseExpression(expression);
        return Boolean.TRUE.equals(rule.parsedExpression.getValue(user, Boolean.class));
    }

    private List<User> filterByExpression(Rule rule, List<User> users){
       if (rule.getExpression() == null || rule.getExpression().isEmpty()){
           return users;
       }
       return users.stream().filter((user) -> match(rule,user)).toList();
    }

    private void constructCache(){
        for (Rule rule: this.availableRules){
            Map<CacheKey, List<User>> usersMap = this.userCache.users.stream()
                    .collect(Collectors.groupingBy(user ->  new CacheKey(rule,user)));
            usersMap.replaceAll(((cacheKey, users) -> filterByExpression(rule,users)));
            ruleCaches.put(rule.getId(),usersMap.entrySet().stream().
                    collect(Collectors.toMap(Map.Entry::getKey,
                            entry->{return new RukeSpecificData(entry.getValue());})));
        }
    }

    @Override
    public RuleMatcherOut filterUsersByRules(AllocationInput input) {
        Map<String, String> filterAttributes = input.allocatableEntity;
        RuleMatcherOut ruleMatcherOut = new RuleMatcherOut();
        for (Rule rule: availableRules){
            if (!input.allocationType.equals(rule.getAllocationType()))
                continue;
            CacheKey cacheKey = CacheKey.constructCacheKey(rule,filterAttributes);
            if (cacheKey == null)
                continue;
            RukeSpecificData val = ruleCaches.get(rule.getId()).get(cacheKey);
            if (val != null){
                ruleMatcherOut.rule = rule;
                ruleMatcherOut.users = val.users;
                ruleMatcherOut.metadata = val.meta;
                return ruleMatcherOut;
            }
        }
        return ruleMatcherOut;
    }
}

package com.vymo.collectiq.allocation.service.rule;

import com.vymo.collectiq.allocation.dto.AllocationInput;
import com.vymo.collectiq.allocation.model.Allocatee;
import com.vymo.collectiq.allocation.model.Rule;
import com.vymo.collectiq.allocation.model.RuleMatcherOut;
import com.vymo.collectiq.allocation.service.source.AllocateeSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RuleSpecificHashCache implements RuleMatcher{
    private static final Logger logger = LoggerFactory.getLogger(RuleSpecificHashCache.class);
    private  RuleSource ruleSource;
    private final Map<String, Map<CacheKey, RuleSpecificData>> ruleCaches = new HashMap<>();
    private final Map<String,List<Allocatee>> sources = new HashMap<>();

    public RuleSpecificHashCache(){}

    public void addInputSource(AllocateeSource source){
        List<Allocatee> list = sources.computeIfAbsent(source.getAllocateeType(), (t) -> new ArrayList<>());
        list.addAll(source.getAllAllocatees());
        sources.put(source.getAllocateeType(),list);
    }

    public void setRuleSource(RuleSource ruleSource){
        this.ruleSource = ruleSource;
    }

    public void loadCache() throws Exception{
        long startTime = System.nanoTime();
        constructCache();
        long endTime = System.nanoTime();
        logger.info("RuleSpecificHashCache: Time to construct the cache: " + (endTime - startTime)/1000 + " micros");
    }

    private final ExpressionParser expressionParser = new SpelExpressionParser();
    public boolean match(Rule rule, Map<String,String> user, Map<String,String> allocatableEntity){
        String expression = rule.getUserExpression();
        if (rule.parsedUserExpression == null)
            rule.parsedUserExpression = expressionParser.parseExpression(expression);
        Map<String,Object> context = new HashMap<>();
        context.put("user",user);
        if (allocatableEntity != null)
            context.put("allocatableEntity",allocatableEntity);
        return Boolean.TRUE.equals(rule.parsedUserExpression.getValue(context, Boolean.class));
    }

    private List<Allocatee> filterByExpression(Rule rule, List<Allocatee> allocatees){
       if (rule.getUserExpression() == null || rule.getUserExpression().isEmpty()){
           return allocatees;
       }
       return allocatees.stream().filter((user) -> match(rule,user,null)).toList();
    }

    private void constructCache(){
        for (Rule rule: this.ruleSource.getRules()){
            List<Allocatee> allocatees = sources.get(rule.allocateeType);
            if (allocatees == null) {
                logger.warn("Cannot find allocatees of type {}", rule.allocateeType);
                continue;
            }
            Map<CacheKey, List<Allocatee>> usersMap = allocatees.stream()
                    .collect(Collectors.groupingBy(user ->  new CacheKey(rule,user)));
            usersMap.replaceAll(((cacheKey, users1) -> filterByExpression(rule,users1)));
            ruleCaches.put(rule.getId(),usersMap.entrySet().stream().
                    collect(Collectors.toMap(Map.Entry::getKey,
                            entry->{return new RuleSpecificData(entry.getValue());})));
        }
    }


    @Override
    public RuleMatcherOut filterUsersByRules(AllocationInput input) {
        Map<String, String> filterAttributes = input.allocatableEntity;
        RuleMatcherOut ruleMatcherOut = new RuleMatcherOut();
        for (Rule rule: ruleSource.getRules()){
            if (!input.allocationType.equals(rule.getAllocationType()))
                continue;
            //  System.out.println("Received case: " + input.allocatableEntity + " Rule = " + rule.getId());
            CacheKey cacheKey = CacheKey.constructCacheKey(rule,filterAttributes);
            // System.out.println("Cache key = " + cacheKey);
            if (cacheKey == null)
                continue;
            RuleSpecificData val = ruleCaches.get(rule.getId()).get(cacheKey);
            // System.out.println("Retrieved users = " + val.users);
            if (val != null){
                ruleMatcherOut.rule = rule;
                ruleMatcherOut.allocatees = filterByAllocatableEntityExpression(val.allocatees,rule, filterAttributes);
                ruleMatcherOut.metadata = val.meta;
                return ruleMatcherOut;
            }
        }
        return ruleMatcherOut;
    }

    private List<Allocatee> filterByAllocatableEntityExpression(List<Allocatee> allocatees, Rule rule, Map<String,String> allocatableEntity) {
        if (rule.getAllocatableEntityExpression() == null || rule.getAllocatableEntityExpression().isEmpty())
            return allocatees;
        return allocatees.stream().filter((user) -> match(rule,user,allocatableEntity)).toList();
    }
}

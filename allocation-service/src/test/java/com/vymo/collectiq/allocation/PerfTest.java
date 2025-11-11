package com.vymo.collectiq.allocation;

import com.vymo.collectiq.allocation.dto.AllocationInput;
import com.vymo.collectiq.allocation.model.RuleMatcherOut;
import com.vymo.collectiq.allocation.model.User;
import com.vymo.collectiq.allocation.service.AllocationService;
import com.vymo.collectiq.allocation.service.allocation.AgencyAllocationWeightages;
import com.vymo.collectiq.allocation.service.rule.RuleMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringTestConfig.class)
@ActiveProfiles("unittest")
public class PerfTest {
    @Autowired @Qualifier("ruleSpecificCache") RuleMatcher ruleSpecificCache;
    @Autowired AllocationService allocationService;
    @Autowired AgencyAllocationWeightages agencyAllocationWeightages;

    @Test
    public void testUserRuleCacheWithNoMatches(){
        Map<String,String> map = new HashMap<>();
        map.put("branch","NYC");
        map.put("product","Checking");
       timeIt(map,ruleSpecificCache);
    }

    public void timeIt(Map<String,String> map,RuleMatcher ruleMatcher){
        System.out.println("Class = " + ruleMatcher.getClass().getName());
        System.out.println("=============================");
        System.out.println("Input = " + map);
        AllocationInput input = new AllocationInput();
        input.allocatableEntity = map;
        long startTime = System.nanoTime();
        RuleMatcherOut ruleMatcherOut = ruleMatcher.filterUsersByRules(input);
        System.out.println("users returned = " + ruleMatcherOut);
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + (endTime - startTime)/1000 + " micros");
    }

    @Test
    public void testUserRuleCacheWithBranchAndProduct(){
        Map<String,String> map = new HashMap<>();
        map.put("branch","branch1");
        map.put("product","product1");
        map.put("entity","entity1");
        timeIt(map,ruleSpecificCache);
    }

    @Test
    public void testUserRuleCacheWith1Match1(){
        Map<String,String> map = new HashMap<>();
        map.put("branch","branch5");
        map.put("product","product0");
        map.put("entity","uu");
        timeIt(map,ruleSpecificCache);
    }

    @Test
    public void testUserRuleCacheWith1Match2(){
        Map<String,String> map = new HashMap<>();
        map.put("branch","branch9");
        map.put("product","product9");
        map.put("entity","entity2");
        timeIt(map,ruleSpecificCache);
    }

    private Map<String,Integer> testCases(String filename, String allocationStrategy,
                                          String allocationType)throws IOException{
        Map<String, Integer> allocationCounts = new HashMap<>();
        try (InputStream inputStream = new FileInputStream(filename)) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                boolean isFirstLine = true;
                String[] headers = null;
                while ((line = br.readLine()) != null) {
                    if (isFirstLine) {
                        headers = line.split(",");
                        // skip header
                        isFirstLine = false;
                        continue;
                    }

                    String[] values = line.split(",");
                    if (values.length >= 6) {
                        Map<String,String> case1 = new User();
                        case1.put(headers[0].trim(), values[0].trim());
                        case1.put(headers[1].trim(), values[1].trim());
                        case1.put(headers[2].trim(), values[2].trim());
                        case1.put(headers[3].trim(), values[3].trim());
                        case1.put(headers[4].trim(), values[4].trim());
                        case1.put(headers[5].trim(), values[5].trim());
                        AllocationInput input = new AllocationInput();
                        input.allocatableEntity = case1;
                        if (allocationStrategy != null)
                            input.allocationStrategy = allocationStrategy;
                        if (allocationType != null)
                            input.allocationType = allocationType;
                        User allocatedUser = allocationService.doAllocation(input);
                        String id = allocatedUser.getId();
                        int count = allocationCounts.computeIfAbsent(id,(id1)-> 0);
                        allocationCounts.put(id,++count);
                    }
                }
            }
        }
        return allocationCounts;
    }

    @Test public void testBulkAllocation() throws IOException {
        String filename = "target/cases.csv";
        System.out.println("Doing Bulk Allocation");
        System.out.println("=============================");
        long startTime = System.nanoTime();
        Map<String,Integer> allocationCounts = testCases(filename,null,null);
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + (endTime - startTime)/1000 + " micros");
        System.out.println("Allocation statistics:");
        System.out.println("Count of all users who have been allocated: " + allocationCounts.size());
        Map<Integer, Long> stats = allocationCounts.values().stream().collect(Collectors.groupingBy((count) -> count,
                Collectors.counting()));
        System.out.println(stats);
        System.out.println("Total count of all cases allocated = " + allocationCounts.values().stream().
                mapToInt(Integer::intValue).sum());
    }

    @Test public void testAgencyAgentDefaultAllocation() throws IOException {
        String filename = "src/test/resources/agent-cases.csv";
        System.out.println("Testing Agent Allocation");
        System.out.println("=============================");
        long startTime = System.nanoTime();
        Map<String,Integer> allocationCounts = testCases(filename,null,"agency-to-agent");
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + (endTime - startTime)/1000 + " micros");
        System.out.println("Allocation stats:" + allocationCounts);
    }

    @Test public void testAgencyAgentWeightedAllocation() throws IOException {
        setAllocationWeightages();
        String filename = "src/test/resources/agent-cases.csv";
        System.out.println("Testing Agent Allocation");
        System.out.println("=============================");
        long startTime = System.nanoTime();
        Map<String,Integer> allocationCounts = testCases(filename,"weighted.allocation","agency-to-agent");
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + (endTime - startTime)/1000 + " micros");
        System.out.println("Allocation stats:" + allocationCounts);
    }

    private void setAllocationWeightages(){
        Map<String,Integer> weightages = new HashMap<>();
        weightages.put("Agent2002",50);
        weightages.put("Agent2001",25);
        weightages.put("Agent2003",20);
        weightages.put("Agent2005",5);
        agencyAllocationWeightages.prep("Agency99",weightages);
    }

    @Test public void testAllocation(){
        Map<String,String> map = new HashMap<>();
        map.put("branch","branch1");
        map.put("product","product1");
        map.put("entity","entity1");
        map.put("territory","territory1");
        map.put("pincode","pincode1");
        AllocationInput input = new AllocationInput();
        input.allocatableEntity = map;
        System.out.println("Allocation service returns " + allocationService.doAllocation(input));
    }
}

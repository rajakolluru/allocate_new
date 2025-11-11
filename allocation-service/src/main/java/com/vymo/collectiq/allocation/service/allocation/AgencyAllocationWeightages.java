package com.vymo.collectiq.allocation.service.allocation;

import org.chenile.base.exception.ErrorNumException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.random.RandomGenerator;

public class AgencyAllocationWeightages {
    private String agencyId;
    RandomGenerator randomGenerator = RandomGenerator.getDefault();
    private final Map<String, String[]> allocationWeightages = new HashMap<>();

    public  void prep( String agency, Map<String,Integer> weightages){
        long sum = weightages.values().stream(). mapToInt(Integer::intValue).sum();
        // System.out.println("Count = " + sum);
        if (sum != 100){
            throw new ErrorNumException(400,62001,"Cant pass weightages that don't add to 100");
        }
        String[] allocationMatrix = new String[100];
        weightages.forEach((key, count) -> {
            for (int i = 0; i < count; i++){
                int selected = randomGenerator.nextInt(100);
                while(allocationMatrix[selected] != null){
                    selected = randomGenerator.nextInt(100);
                }
                allocationMatrix[selected] = key;
            }
        });
        allocationWeightages.put(agency,allocationMatrix);
        // System.out.println(Arrays.toString(allocationMatrix));
    }

    public String[] obtainAllocationMatrix(String agency){
        return allocationWeightages.get(agency);
    }
}

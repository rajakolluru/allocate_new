package com.vymo.collectiq.allocation.service.rule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vymo.collectiq.allocation.model.Rule;
import org.chenile.base.exception.ErrorNumException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;

public class RuleSource {
    private List<Rule> allRules;
    public RuleSource(String path) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new ErrorNumException(500,1000,"rules.json not found in resources folder!");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            List<Rule> rules  = objectMapper.readValue(
                    inputStream,
                    new TypeReference<>() {
                    }
            );
            rules.sort(Comparator.comparingInt(o -> o.priority));
            this.allRules = rules;
            // Print to verify
            this.allRules.forEach(System.out::println);
        }
    }
    public List<Rule> getRules(){
        return allRules;
    }
}

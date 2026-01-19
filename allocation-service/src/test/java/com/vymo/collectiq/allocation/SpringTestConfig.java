package com.vymo.collectiq.allocation;

import com.vymo.collectiq.allocation.generator.GenAgencies;
import com.vymo.collectiq.allocation.generator.GenCases;
import com.vymo.collectiq.allocation.generator.GenUsers;
import com.vymo.collectiq.allocation.model.Allocatee;
import com.vymo.collectiq.allocation.service.allocation.counts.AllocationCountTracker;
import com.vymo.collectiq.allocation.service.rule.RuleSource;
import com.vymo.collectiq.allocation.service.rule.RuleSpecificHashCache;
import com.vymo.collectiq.allocation.service.source.AgencySource;
import com.vymo.collectiq.allocation.service.source.UserSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static com.vymo.collectiq.allocation.service.load.LoaderUtil.loadUsers;

@Configuration
@PropertySource("classpath:com/vymo/collectiq/allocation/TestService.properties")
@SpringBootApplication(scanBasePackages = {  "org.chenile.configuration", "com.vymo.collectiq.allocation.configuration" })
@ActiveProfiles("unittest")
public class SpringTestConfig extends SpringBootServletInitializer {
    @Autowired
    AllocationCountTracker allocationCache;
    @Autowired RuleSpecificHashCache ruleSpecificHashCache;
    static {
        try {
            GenUsers.main(null);
            GenCases.main(null);
            GenAgencies.main(null);
        }catch(Exception e){
            throw new RuntimeException("Could not build the Users and Cases DB",e);
        }
    }

    @Bean UserSource userSource() throws IOException {
        List<Allocatee> allocatees = loadUsers("target/users1.csv");
        allocatees.addAll(loadUsers("src/test/resources/users.csv"));
        UserSource source = new UserSource(allocatees);
        ruleSpecificHashCache.addInputSource(source);
        allocationCache.load(source.getAllocateeType(),allocatees);
        return source;
    }

    @Bean AgencySource agencySource() throws IOException {
        List<Allocatee> allocatees = loadUsers("target/agencies.csv");
        AgencySource agencies = new AgencySource(allocatees);
        ruleSpecificHashCache.addInputSource(agencies);
        allocationCache.load(agencies.getAllocateeType(),allocatees);
        return agencies;
    }

    @Bean
    RuleSource ruleSource() throws IOException {
        RuleSource r = new RuleSource("/rules.json");
        ruleSpecificHashCache.setRuleSource(r);
        return  r;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() throws Exception {
        ruleSpecificHashCache.loadCache();
    }
}


package com.vymo.collectiq.allocation;

import com.vymo.collectiq.allocation.generator.GenCases;
import com.vymo.collectiq.allocation.generator.GenUsers;
import com.vymo.collectiq.allocation.model.Rule;
import com.vymo.collectiq.allocation.model.User;
import com.vymo.collectiq.allocation.service.allocation.counts.AllocationCache;
import com.vymo.collectiq.allocation.service.rule.RuleSpecificHashCache;
import com.vymo.collectiq.allocation.service.users.UsersDB;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.vymo.collectiq.allocation.service.load.LoaderUtil.loadRules;
import static com.vymo.collectiq.allocation.service.load.LoaderUtil.loadUsers;

@Configuration
@PropertySource("classpath:com/vymo/collectiq/allocation/TestService.properties")
@SpringBootApplication(scanBasePackages = {  "org.chenile.configuration", "com.vymo.collectiq.allocation.configuration" })
@ActiveProfiles("unittest")
public class SpringTestConfig extends SpringBootServletInitializer implements InitializingBean {
    @Autowired AllocationCache allocationCache;
    @Autowired RuleSpecificHashCache ruleSpecificHashCache;
    @Autowired UsersDB usersDB;
    static {
        try {
            GenUsers.main(null);
            GenCases.main(null);
        }catch(Exception e){
            throw new RuntimeException("Could not build the Users and Cases DB",e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<User> users = loadUsers("target/users1.csv");
        users.addAll(loadUsers("src/test/resources/users.csv"));
        usersDB.load(users);
        List<Rule> rules = loadRules("/rules.json");
        ruleSpecificHashCache.load(users,rules);
        allocationCache.load(users);
    }
}


package com.vymo.collectiq.allocation;

import com.vymo.collectiq.allocation.generator.GenCases;
import com.vymo.collectiq.allocation.generator.GenUsers;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
@Configuration
@PropertySource("classpath:com/vymo/collectiq/allocation/TestService.properties")
@SpringBootApplication(scanBasePackages = {  "org.chenile.configuration", "com.vymo.collectiq.allocation.configuration" })
@ActiveProfiles("unittest")
public class SpringTestConfig extends SpringBootServletInitializer{
    static {
        try {
            GenUsers.main(null);
            GenCases.main(null);
        }catch(Exception e){
            throw new RuntimeException("Could not build the Users and Cases DB",e);
        }
    }
}


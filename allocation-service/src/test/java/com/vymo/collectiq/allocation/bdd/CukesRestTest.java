package com.vymo.collectiq.allocation.bdd;

import com.vymo.collectiq.allocation.generator.GenCases;
import com.vymo.collectiq.allocation.generator.GenUsers;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
		glue = {"classpath:com/vymo/collectiq/allocation/bdd", "classpath:org/chenile/cucumber/rest"},
        plugin = {"pretty"}
        )
@ActiveProfiles("unittest")
public class CukesRestTest {
}

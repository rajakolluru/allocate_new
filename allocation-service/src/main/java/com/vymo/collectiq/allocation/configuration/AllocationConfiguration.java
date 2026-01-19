package com.vymo.collectiq.allocation.configuration;

import com.vymo.collectiq.allocation.service.AllocationService;
import com.vymo.collectiq.allocation.service.allocation.*;
import com.vymo.collectiq.allocation.service.allocation.counts.AllocationCountTracker;
import com.vymo.collectiq.allocation.service.check.ThresholdChecker;
import com.vymo.collectiq.allocation.service.check.ThresholdCheckerImpl;
import com.vymo.collectiq.allocation.service.config.ConfigServerImpl;
import com.vymo.collectiq.allocation.service.healthcheck.AllocationHealthChecker;
import com.vymo.collectiq.allocation.service.impl.AllocationServiceImpl;
import com.vymo.collectiq.allocation.service.rule.RuleSpecificHashCache;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration

public class AllocationConfiguration {

	@Bean public AllocationService _allocationService_() {
		return new AllocationServiceImpl();
	}

	@Bean AllocationHealthChecker allocationHealthChecker(){
    	return new AllocationHealthChecker();
    }

	@Bean
	RuleSpecificHashCache ruleSpecificCache() throws Exception{
		return new RuleSpecificHashCache();
	}

	@Bean
	AllocationStrategyFactory allocationStrategyFactory() throws Exception{
		return new AllocationStrategyFactory();
	}

	@Bean
	AllocationStrategy randomAllocationStrategy(AllocationStrategyFactory factory) throws Exception{
		AllocationStrategy allocationStrategy = new RandomAllocationStrategy();
		factory.registerAllocationStrategy("random",allocationStrategy);
		return allocationStrategy;
	}

	@Bean
	AllocationStrategy leastUsedAllocationStrategy(AllocationStrategyFactory factory) throws Exception{
		AllocationStrategy allocationStrategy = new LeastUsedAllocationStrategy();
		factory.registerAllocationStrategy("least.used",allocationStrategy);
		return allocationStrategy;
	}

	@Bean
	AllocationStrategy roundRobinAllocationStrategy(AllocationStrategyFactory factory) throws Exception{
		AllocationStrategy allocationStrategy = new RoundRobinStrategy();
		factory.registerAllocationStrategy("round.robin",allocationStrategy);
		return allocationStrategy;
	}

	@Bean
	AllocationStrategy weightedAllocationStrategy(AllocationStrategyFactory factory) throws Exception{
		AllocationStrategy allocationStrategy = new WeightedAllocationStrategy();
		factory.registerAllocationStrategy("weighted.allocation",allocationStrategy);
		return allocationStrategy;
	}

	@Bean
	ThresholdChecker thresholdChecker() throws Exception{
		return new ThresholdCheckerImpl();
	}

	@Bean
	AllocationCountTracker allocationCache() throws Exception{
		return new AllocationCountTracker();
	}

	@Bean
	@ConfigurationProperties(prefix = "allocation")
	ConfigServerImpl configServer() throws Exception{
		return new ConfigServerImpl();
	}

	@Bean AgencyAllocationWeightages agencyAllocationWeightages(){
		return new AgencyAllocationWeightages();
	}

}

package com.dyl.prime.config;

import com.dyl.prime.model.PrimeGenAlgorithm;
import com.dyl.prime.service.UniqueNumberSeqCacheService;
import com.dyl.prime.service.generator.BruteForcePrimeSeqGenerator;
import com.dyl.prime.service.generator.IPrimeSeqGenerator;
import com.dyl.prime.service.PrimeService;
import com.dyl.prime.service.generator.OptimisedBruteForcePrimeSeqGenerator;
import com.dyl.prime.service.generator.SievePrimeSeqGenerator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ServiceConfiguration {

    private ExecutorService executorService = Executors.newFixedThreadPool(100);

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public UniqueNumberSeqCacheService sortedUniqueNumberSeqCacheService() {
        return new UniqueNumberSeqCacheService();
    }

    @Bean
    public PrimeService primeService(UniqueNumberSeqCacheService uniqueNumberSeqCacheService) {

        Map<PrimeGenAlgorithm, IPrimeSeqGenerator> algorithmMap = new HashMap<>();

        algorithmMap.put(PrimeGenAlgorithm.BRUTE_FORCE,
                new BruteForcePrimeSeqGenerator(executorService, uniqueNumberSeqCacheService));

        algorithmMap.put(PrimeGenAlgorithm.OPTIMISED_BRUTE_FORCE,
                new OptimisedBruteForcePrimeSeqGenerator(executorService, uniqueNumberSeqCacheService));

        algorithmMap.put(PrimeGenAlgorithm.SIEVE,
                new SievePrimeSeqGenerator(executorService, uniqueNumberSeqCacheService));


        return new PrimeService(algorithmMap);
    }

}

package com.dyl.prime.service.generator;

import com.dyl.prime.service.UniqueNumberSeqCacheService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class OptimisedBruteForcePrimeSeqGenerator extends AbstractBruteForcePrimeSeqGenerator {

    public OptimisedBruteForcePrimeSeqGenerator(ExecutorService executorService,
                                                UniqueNumberSeqCacheService uniqueNumberSeqCacheService) {
        super(executorService, uniqueNumberSeqCacheService);
    }

    @Override
    List<Integer> getPrimeSequenceForBoundry(int startOfRange, int endOfRange) {
        List<Integer> primes = new ArrayList<>();

        if(startOfRange<=2 && endOfRange>=2) {
            primes.add(2);
        }

        // start number shouldn't be smaller than 3 for this algorithm to work.
        startOfRange = startOfRange < 3 ? 3 : startOfRange;

        // This algorithm will skip even numbers so we need to make sure our starting number is not even
        startOfRange = startOfRange % 2 == 0 ? startOfRange + 1 : startOfRange;


        for(int i=startOfRange; i<=endOfRange; i=i+2) {
            if(isPrime(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    private boolean isPrime(int number) {
        for(int i = 2; i * i <= number; i++) {
            if(number % i == 0) {
                return false;
            }
        }
        return true;
    }

}



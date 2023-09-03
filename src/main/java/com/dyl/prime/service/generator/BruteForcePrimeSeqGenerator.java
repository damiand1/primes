package com.dyl.prime.service.generator;

import com.dyl.prime.service.UniqueNumberSeqCacheService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Class generating sequence of prime numbers using a simple brute force method.
 */
public class BruteForcePrimeSeqGenerator extends AbstractBruteForcePrimeSeqGenerator {

    public BruteForcePrimeSeqGenerator(ExecutorService executorService,
                                               UniqueNumberSeqCacheService uniqueNumberSeqCacheService) {
        super(executorService, uniqueNumberSeqCacheService);
    }

    @Override
    List<Integer> getPrimeSequenceForBoundry(int startOfRange, int endOfRange) {
        List<Integer> primes = new ArrayList<>();

        // For this algorithm to work start of range should never be less than 2
        startOfRange = startOfRange < 2 ? 2: startOfRange;

        for(int i=startOfRange; i<=endOfRange; i++) {
            if(isPrime(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    private boolean isPrime(int number) {
        for(int i = 2; i < number; i++) {
            if(number % i == 0) {
                return false;
            }
        }
        return true;
    }
}

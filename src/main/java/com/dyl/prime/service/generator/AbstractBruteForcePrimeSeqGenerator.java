package com.dyl.prime.service.generator;

import com.dyl.prime.model.exception.InvalidPrimeNumberRangeException;
import com.dyl.prime.service.UniqueNumberSeqCacheService;
import com.dyl.prime.util.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class AbstractBruteForcePrimeSeqGenerator implements IPrimeSeqGenerator {

    private ExecutorService executorService;
    private UniqueNumberSeqCacheService uniqueNumberSeqCacheService;

    public AbstractBruteForcePrimeSeqGenerator(ExecutorService executorService,
                                               UniqueNumberSeqCacheService uniqueNumberSeqCacheService) {
        this.executorService = executorService;
        this.uniqueNumberSeqCacheService = uniqueNumberSeqCacheService;
    }

    public List<Integer> getPrimeSequence(int endOfRange) {
        if(endOfRange < 2) {
            return new ArrayList<>();
        }
        List<Integer> primes = new ArrayList<>();
        List<Integer> cachedPrimes = uniqueNumberSeqCacheService.getCachedNumberSequence();

        int largestCachedPrime = cachedPrimes.isEmpty() ? 0 : ListUtils.getLastElement(cachedPrimes);
        // The whole sequence of prime numbers is in the cache
        // We will get the numbers we need and exit early.
        if(endOfRange <= largestCachedPrime) {
            for(Integer cachedPrime : cachedPrimes) {
                if(cachedPrime <= endOfRange) {
                    primes.add(cachedPrime);
                } else {
                    return primes;
                }
            }
        // Part of the sequence is in the cache or the cache is empty
        // We will generate remaining numbers and add them to cache
        } else {
            primes.addAll(cachedPrimes);
            int startOfRange = cachedPrimes.isEmpty() ? 0 : largestCachedPrime + 1;

            int numbersToCheckCount = (endOfRange - startOfRange) + 1;

            List<Integer> missingPrimes = new ArrayList<>();

            // If we only have a single number to check we will not do multithreading
            if(numbersToCheckCount < 2) {
                missingPrimes.addAll(getPrimeSequenceForBoundry(startOfRange,
                        endOfRange));
            // We have more than one number to check so we will split the range into two
            // and do two parts concurrently.
            } else {
                // Use int so we skip decimal point
                int midPoint = startOfRange + (numbersToCheckCount / 2);

                try {
                    Future<List<Integer>> firstProcessor = executorService.submit(getPrimeSequenceForBoundryTask(startOfRange,
                            midPoint-1));
                    Future<List<Integer>> secondProcessor = executorService.submit(getPrimeSequenceForBoundryTask(midPoint,
                            endOfRange));

                    missingPrimes.addAll(firstProcessor.get());
                    missingPrimes.addAll(secondProcessor.get());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            primes.addAll(missingPrimes);

            if(!missingPrimes.isEmpty())
            uniqueNumberSeqCacheService.addNumberSequence(missingPrimes);
        }

        return primes;
    }


    private Callable<List<Integer>> getPrimeSequenceForBoundryTask(
            final int startOfRange,
            final int endOfRange) {

        return () -> {
            return getPrimeSequenceForBoundry(startOfRange, endOfRange);
        };
    }
    abstract List<Integer> getPrimeSequenceForBoundry(int startOfRange, int endOfRange);

}

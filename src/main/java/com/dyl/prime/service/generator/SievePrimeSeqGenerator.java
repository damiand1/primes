package com.dyl.prime.service.generator;

import com.dyl.prime.model.exception.InvalidPrimeNumberRangeException;
import com.dyl.prime.service.UniqueNumberSeqCacheService;
import com.dyl.prime.util.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class SievePrimeSeqGenerator implements IPrimeSeqGenerator {

    private UniqueNumberSeqCacheService uniqueNumberSeqCacheService;
    private ExecutorService executorService;

    public SievePrimeSeqGenerator(ExecutorService executorService, UniqueNumberSeqCacheService uniqueNumberSeqCacheService) {
        this.executorService = executorService;
        this.uniqueNumberSeqCacheService = uniqueNumberSeqCacheService;
    }

    @Override
    public List<Integer> getPrimeSequence(int endOfRange) {

        if(endOfRange < 2) {
            return new ArrayList<>();
        }

        List<Integer> primes = new ArrayList<>();
        List<Integer> cachedPrimes = uniqueNumberSeqCacheService.getCachedNumberSequence();

        int largestCachedPrime = cachedPrimes.isEmpty() ? 0 : ListUtils.getLastElement(cachedPrimes);
        // Cache has full primes sequence so we can return it
        if(endOfRange <= largestCachedPrime) {
            for(Integer cachedPrime : cachedPrimes) {
                if(cachedPrime <= endOfRange) {
                    primes.add(cachedPrime);
                } else {
                    return primes;
                }
            }
        }

        // otherwise calculate new sequence of primes
        boolean primesSieve[] = new boolean[endOfRange + 1];

        Arrays.fill(primesSieve, true);

        // Sieve checking goes in increments. We only check up to square root of  the end range as
        // any number cannot consist of two factors that are both greater than it's square root.
        for(int incrementValue = 2; incrementValue * incrementValue <= endOfRange; incrementValue++) {
            if(primesSieve[incrementValue]) {
                int nextNumberToMarkAsNotPrime = incrementValue*2;
                // We have at least two numbers to mark so we will do it concurrently.
                if(nextNumberToMarkAsNotPrime + incrementValue <= endOfRange) {

                    // Split execution into two concurrent streams to improve performance
                    int midPoint = getMidpointBoundry(incrementValue, incrementValue, endOfRange);
                    Future<?> firstProcessor = executorService.submit(markAsNotPrimeTask(primesSieve, incrementValue*2, midPoint, incrementValue));
                    Future<?> secondProcessor = executorService.submit(markAsNotPrimeTask(primesSieve, midPoint + incrementValue, endOfRange, incrementValue));

                    try {
                        secondProcessor.get();
                        firstProcessor.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                // We have only one number to mark so we will not use concurrency
                } else if(nextNumberToMarkAsNotPrime <= endOfRange) {
                    primesSieve[nextNumberToMarkAsNotPrime] = false;
                }
            }
        }

        for(int i=2; i < primesSieve.length; i++) {
            if(primesSieve[i]) {
                primes.add(i);
            }
        }

        List<Integer> missingPrimes = new ArrayList<>();

        for(Integer prime : primes) {
            if(!cachedPrimes.contains(prime)) {
                missingPrimes.add(prime);
            }
        }
        // simplified cache update
        if(!missingPrimes.isEmpty())
        uniqueNumberSeqCacheService.addNumberSequence(missingPrimes);

        return primes;
    }


    /**
     * Method to calculate the boundry where the first thread should finish and second thread should pick up. The first thread will
     * fill the multiplications of incrementValue from startingpoint (exclusive) up to the boundry (inclusive)
     * The second thread will handle the multiplications of the incrementValue starting from the boundry (exclusive)
     * up to end of range (inclusive)
     * Example: startingPoint = 2; In which case 2 is a prime number. All of multiplications of it are not prime numbers.
     * Range - 11; Multiplications are 4,6,8,10
     * The boundry is "6"
     * First thread will start on 4 and go up to 6 inclusive. Mark 4, 6
     * Second thread will start at 8 incluive and finish at 11. Mark 8, 10
     */
    private int getMidpointBoundry(int startingPoint, int incrementValue, int endOfRange) {

        // first of all calculate how many multiplications of incrementValue can
        // we fit between startingPoint(exclusive) and endOfRange(inclusive
        int multiplicationsCount = (endOfRange - startingPoint) / incrementValue;

        //Split the number of multiplications into two to work out how many numbers should
        //be before the midpoint. Use int so we skip the decimal point
        int numberOfMultiplicationsBeforeMidpoint = multiplicationsCount / 2;

        // midpoint boundry is the number of half of multiplications of given number for range
        // between starting point and end of range. Use int so we skip the decimal point
        int midpointBoundry = startingPoint + (numberOfMultiplicationsBeforeMidpoint * incrementValue);

        return midpointBoundry;
    }

    private Runnable markAsNotPrimeTask(final boolean primesSieve[],
                                        final int startOfRange,
                                        final int endOfRange, int increment) {
        return () -> {
            for(int j = startOfRange; j <= endOfRange; j+=increment) {
                primesSieve[j] = false;
            }
        };
    }
}

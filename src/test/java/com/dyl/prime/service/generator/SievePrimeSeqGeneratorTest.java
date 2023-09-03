package com.dyl.prime.service.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.Executors;

@ExtendWith(MockitoExtension.class)
public class SievePrimeSeqGeneratorTest extends AbstractPrimeGeneratorTest{


    @BeforeEach
    public void setup() {
        executorService = Executors.newFixedThreadPool(10);
        target = new SievePrimeSeqGenerator(executorService, uniqueNumberSeqCacheService);
    }

    @Test
    public void getPrimeSequenceForBoundry_thousandRange_shouldReturnSortedSequenceOfPrimes() {
        assertPrimeSequenceCachedAndReturned(1000);
    }

    @Test
    public void getPrimeSequenceForBoundry_oneRange_shouldReturnEmptyList() {
        assertNoPrimesGeneratedForRange1();
    }

    @Test
    public void getPrimeSequenceForBoundry_twoRange_shouldReturnValidPrimes() {
       assertPrimeSequenceCachedAndReturned(2);
    }

    @Test
    public void getPrimeSequenceForBoundry_threeRange_shouldReturnValidPrimes() {
        assertPrimeSequenceCachedAndReturned(3);
    }

    @Test
    public void getPrimeSequenceForBoundry_fiveRange_shouldReturnValidPrimes() {
        assertPrimeSequenceCachedAndReturned(5);
    }

    @Test
    public void getPrimeSequenceForBoundry_singleCall_shouldCacheResults() {
        assertPrimeSequenceCachedAndReturned(1000);
    }

    @Test
    public void getPrimeSequenceForBoundry_nonEvenRange_shouldReturnValidPrimes() {
        assertPrimeSequenceCachedAndReturned(1001);
    }

    @Test
    public void getPrimeSequenceForBoundry_doubleCallWithDifferentRanges_shouldCacheResultsAndAppendSecondResults() {
        assertPrimeSequenceAndCacheReturnedForMultipleCalls();
    }
}

package com.dyl.prime.service.generator;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;


import com.dyl.prime.service.UniqueNumberSeqCacheService;
import com.dyl.prime.testUtil.PrimeAssertionUtils;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class AbstractPrimeGeneratorTest {

    @Mock
    protected UniqueNumberSeqCacheService uniqueNumberSeqCacheService;

    @Captor
    protected ArgumentCaptor<List<Integer>> primeSequenceCaptor;

    protected ExecutorService executorService;

    protected IPrimeSeqGenerator target;


    protected void assertNoPrimesGeneratedForRange1() {
        List<Integer> result = target.getPrimeSequence(1);
        assertThat(result, is(empty()));
        verify(uniqueNumberSeqCacheService, never()).addNumberSequence(any());
    }

    protected void assertPrimeSequenceCachedAndReturned(int endOfRange) {

        List<Integer> result = target.getPrimeSequence(endOfRange);
        List<Integer> expectedPrimeSequence = PrimeAssertionUtils.getPrimeSequenceForRange(endOfRange);
        assertThat(result, containsInAnyOrder(expectedPrimeSequence.toArray()));
        assertThat(result.size(), is(equalTo(expectedPrimeSequence.size())));

        verify(uniqueNumberSeqCacheService, times(1)).addNumberSequence(primeSequenceCaptor.capture());

        List<Integer> updateCachePrimeSequence = primeSequenceCaptor.getValue();

        assertThat(updateCachePrimeSequence, containsInRelativeOrder(expectedPrimeSequence.toArray()));
        assertThat(updateCachePrimeSequence.size(), is(equalTo(expectedPrimeSequence.size())));
    }

    protected void assertPrimeSequenceAndCacheReturnedForMultipleCalls() {
        when(uniqueNumberSeqCacheService.getCachedNumberSequence()).thenReturn(new ArrayList<>()).thenReturn(PrimeAssertionUtils.getPrimeSequenceForRange(500));
        List<Integer> result500 = target.getPrimeSequence(500);
        List<Integer> expectedPrimeSequence500 = PrimeAssertionUtils.getPrimeSequenceForRange(500);

        assertThat(result500, containsInAnyOrder(expectedPrimeSequence500.toArray()));
        assertThat(result500.size(), is(equalTo(expectedPrimeSequence500.size())));

        List<Integer> result1000 = target.getPrimeSequence(1000);
        List<Integer> expectedPrimeSequence1000 = PrimeAssertionUtils.getPrimeSequenceForRange(1000);

        assertThat(result1000, containsInAnyOrder(expectedPrimeSequence1000.toArray()));
        assertThat(result1000.size(), is(equalTo(expectedPrimeSequence1000.size())));



        verify(uniqueNumberSeqCacheService, times(2)).addNumberSequence(primeSequenceCaptor.capture());

        List<Integer> updateCachePrimeSequence500 = primeSequenceCaptor.getAllValues().get(0);

        assertThat(updateCachePrimeSequence500, containsInRelativeOrder(expectedPrimeSequence500.toArray()));
        assertThat(updateCachePrimeSequence500.size(), is(equalTo(expectedPrimeSequence500.size())));

        List<Integer> updateCachePrimeSequence1000 = primeSequenceCaptor.getAllValues().get(1);
        assertThat(updateCachePrimeSequence1000, containsInRelativeOrder(PrimeAssertionUtils.getPrimesFor500to1000().toArray()));
        assertThat(updateCachePrimeSequence1000.size(), is(equalTo(PrimeAssertionUtils.getPrimesFor500to1000().size())));
    }

}

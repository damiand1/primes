package com.dyl.prime.service;

import com.dyl.prime.util.ListUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class UniqueNumberSeqCacheServiceTest {

    private UniqueNumberSeqCacheService target;

    @BeforeEach
    public void setup() {
        target = new UniqueNumberSeqCacheService();
    }

    @Test
    public void addNumberSequence_numberSequence_shouldPersistNumberSequence() {
        List<Integer> numbersToCache = ListUtils.newList(1,2,3);
        target.addNumberSequence(numbersToCache);

        List<Integer> cachedNumbers = target.getCachedNumberSequence();

        assertThat(cachedNumbers, containsInRelativeOrder(numbersToCache.toArray()));
    }

    @Test
    public void addNumberSequence_unsortedNumberSequence_shouldPersistNumberSequenceInSortedOrder() {
        List<Integer> numbersToCache = ListUtils.newList(1,2,3,4);
        target.addNumberSequence(numbersToCache);

        List<Integer> cachedNumbers = target.getCachedNumberSequence();

        assertThat(cachedNumbers, containsInRelativeOrder(ListUtils.newList(1,2,3,4).toArray()));
    }

    @Test
    public void addNumberSequence_duplicateNumber_shouldPersistNumbersAndIgnoreDuplicates() {
        target.addNumberSequence(ListUtils.newList(1,2,3,4));
        target.addNumberSequence(ListUtils.newList(1,2,3,4,5,6,7,8));

        List<Integer> cachedNumbers = target.getCachedNumberSequence();

        assertThat(cachedNumbers, containsInRelativeOrder(ListUtils.newList(1,2,3,4,5,6,7,8).toArray()));
        assertThat(cachedNumbers.size(), is(equalTo(8)));
    }
}



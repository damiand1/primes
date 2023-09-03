package com.dyl.prime.service;

import lombok.Synchronized;
import org.springframework.scheduling.annotation.Async;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Simple Unique Number Sequence Cache. It allows to add numbers to the cache or replace its content completely.
 * The modification of cache is synchronized so that only one modification can happen at time. This is because
 * the underlying LinkedHashSet is not thread safe.
 * The modificattion of cache is also asynchronous which means that the request wont wait for the cache modification to complete.
 * The fire and forget logic allows to serve requests faster.
 */
public class UniqueNumberSeqCacheService {
    private LinkedHashSet<Integer> cache = new LinkedHashSet<>();
    private int largestNumber = 0;

    @Async
    @Synchronized
    public void addNumberSequence(List<Integer> numberSequence) {
        if(numberSequence.isEmpty()) {
            return;
        }

        List<Integer> sortedNumberSequence = numberSequence
                .stream()
                .sorted()
                .collect(Collectors.toList());

        int lastNumber = sortedNumberSequence.get(sortedNumberSequence.size()-1);

        if(lastNumber > largestNumber) {
            sortedNumberSequence.stream().forEach(cache::add);
            largestNumber = lastNumber;
        }
    }

    public List<Integer> getCachedNumberSequence() {
       List<Integer> cachedNumberSequence = new ArrayList<>();
       cache.stream().forEach(cachedNumberSequence::add);
       return cachedNumberSequence;
    }

}

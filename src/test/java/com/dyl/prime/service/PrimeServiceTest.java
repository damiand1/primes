package com.dyl.prime.service;

import com.dyl.prime.model.PrimeGenAlgorithm;
import com.dyl.prime.model.exception.AlgorithmNotSupportedException;
import com.dyl.prime.model.exception.InvalidPrimeNumberRangeException;
import com.dyl.prime.service.generator.BruteForcePrimeSeqGenerator;
import com.dyl.prime.service.generator.IPrimeSeqGenerator;
import com.dyl.prime.util.ListUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrimeServiceTest {

    @Mock
    private BruteForcePrimeSeqGenerator bruteForcePrimeSeqGenerator;

    private PrimeService target;

    @BeforeEach
    public void setup() {
        Map<PrimeGenAlgorithm, IPrimeSeqGenerator> primeGenerators = new HashMap<>();
        primeGenerators.put(PrimeGenAlgorithm.BRUTE_FORCE, bruteForcePrimeSeqGenerator);

        target = new PrimeService(primeGenerators);
    }

    @Test
    public void generatePrimeSequence_validPrimeRange_callsPrimeGenerator() {
        when(bruteForcePrimeSeqGenerator.getPrimeSequence(anyInt())).thenReturn(ListUtils.newList(1,2,3));

        List<Integer> primes = target.generatePrimeSequence(PrimeGenAlgorithm.BRUTE_FORCE, 10);

        verify(bruteForcePrimeSeqGenerator, times(1)).getPrimeSequence((eq(10)));

        assertThat(primes, containsInRelativeOrder(ListUtils.newList(1,2,3).toArray()));
    }


    @Test
    public void generatePrimeSequence_negativeRange_throwsException() {
        Assertions.assertThrows(InvalidPrimeNumberRangeException.class, () -> {
            target.generatePrimeSequence(PrimeGenAlgorithm.BRUTE_FORCE, -10);
        });
    }

    public void generatePrimeSequence_notImplementedAlgorithm_throwsException() {
        Assertions.assertThrows(AlgorithmNotSupportedException.class, () -> {
            target.generatePrimeSequence(PrimeGenAlgorithm.SIEVE, 10);
        });
    }

}

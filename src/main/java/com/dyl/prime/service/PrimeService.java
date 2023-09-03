package com.dyl.prime.service;

import com.dyl.prime.model.PrimeGenAlgorithm;
import com.dyl.prime.model.exception.AlgorithmNotSupportedException;
import com.dyl.prime.model.exception.InvalidPrimeNumberRangeException;
import com.dyl.prime.service.generator.IPrimeSeqGenerator;

import java.util.List;
import java.util.Map;

public class PrimeService {
    private Map<PrimeGenAlgorithm, IPrimeSeqGenerator> primeGenerators;

    public PrimeService(Map<PrimeGenAlgorithm, IPrimeSeqGenerator> primeGenerators) {
        this.primeGenerators = primeGenerators;
    }

    public List<Integer> generatePrimeSequence(PrimeGenAlgorithm primeGenAlgorithm,
                                               int endOfRange) {

        if (endOfRange < 0) {
            throw new InvalidPrimeNumberRangeException("Prime number range has to be a positive integer!");
        }

        if(!primeGenerators.containsKey(primeGenAlgorithm)) {
            throw new AlgorithmNotSupportedException("Specified Algorithm is not supported.");
        }

        IPrimeSeqGenerator primeSeqGenerator = primeGenerators.get(primeGenAlgorithm);

        return primeSeqGenerator.getPrimeSequence(endOfRange);

    }
}

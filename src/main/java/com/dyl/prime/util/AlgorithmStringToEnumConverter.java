package com.dyl.prime.util;

import com.dyl.prime.model.PrimeGenAlgorithm;
import com.dyl.prime.model.exception.AlgorithmNotSupportedException;
import org.springframework.core.convert.converter.Converter;

public class AlgorithmStringToEnumConverter implements Converter<String, PrimeGenAlgorithm> {
    @Override
    public PrimeGenAlgorithm convert(String source) {
        for(PrimeGenAlgorithm primeGenAlgorithm : PrimeGenAlgorithm.values()) {
            if(primeGenAlgorithm.name().equalsIgnoreCase(source)) {
                return primeGenAlgorithm;
            }
        }

        throw new AlgorithmNotSupportedException(("Unable to find Prime Number Generation Algorithm:" + source));
    }
}

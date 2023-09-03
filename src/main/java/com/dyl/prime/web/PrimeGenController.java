package com.dyl.prime.web;

import com.dyl.prime.model.PrimeGenAlgorithm;
import com.dyl.prime.model.PrimeSequenceResponse;
import com.dyl.prime.service.PrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/primes")
public class PrimeGenController {

    private PrimeService primeService;

    @Autowired
    public PrimeGenController(PrimeService primeService) {
        this.primeService = primeService;
    }

    @GetMapping(value="/{initial}",
    produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.ALL_VALUE})
    public PrimeSequenceResponse getPrimeSequence(
            @PathVariable Integer initial,
            @RequestParam(name = "algorithm", defaultValue = "BRUTE_FORCE") PrimeGenAlgorithm algorithm) {


        return new PrimeSequenceResponse()
                .setPrimes(primeService.generatePrimeSequence(algorithm, initial))
                .setInitial(initial);
    }
}

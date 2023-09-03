package com.dyl.prime.web;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class PrimeGenControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void primes_noAlgorithm_shouldReturnValidListOfPrimes() throws Exception {
        this.mockMvc.perform(get("/primes/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"initial\":10,\"primes\":[2,3,5,7]}"));
    }

    @Test
    public void primes_bruteForceAlgorithm_shouldReturnValidListOfPrimes() throws Exception {
        this.mockMvc.perform(get("/primes/10").param("algorithm", "brute_force"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"initial\":10,\"primes\":[2,3,5,7]}"));
    }

    @Test
    public void primes_optimisedBruteForceAlgorithm_shouldReturnValidListOfPrimes() throws Exception {
        this.mockMvc.perform(get("/primes/10").param("algorithm", "optimised_brute_force"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"initial\":10,\"primes\":[2,3,5,7]}"));
    }

    @Test
    public void primes_sieveAlgorithm_shouldReturnValidListOfPrimes() throws Exception {
        this.mockMvc.perform(get("/primes/10").param("algorithm", "sieve"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"initial\":10,\"primes\":[2,3,5,7]}"));
    }

    @Test
    public void primes_noContentHeader_shouldReturnValidListOfPrimesAsJson() throws Exception {
        this.mockMvc.perform(get("/primes/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"initial\":10,\"primes\":[2,3,5,7]}"));
    }

    @Test
    public void primes_jsonContentHeader_shouldReturnValidListOfPrimesAsJson() throws Exception {
        this.mockMvc.perform(get("/primes/10").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"initial\":10,\"primes\":[2,3,5,7]}"));
    }

    @Test
    public void primes_xmlContentHeader_shouldReturnValidListOfPrimesAsXML() throws Exception {
        this.mockMvc.perform(get("/primes/10").contentType(MediaType.APPLICATION_XML_VALUE).accept(MediaType.APPLICATION_XML_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("<PrimeSequenceResponse><Initial>10</Initial><Primes><Prime>2</Prime><Prime>3</Prime><Prime>5</Prime><Prime>7</Prime></Primes></PrimeSequenceResponse>"));
    }

    @Test
    public void primes_initialBelow2_shouldReturnEmptyResponse() throws Exception {
        this.mockMvc.perform(get("/primes/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"initial\":1,\"primes\":[]}"));
    }

    @Test
    public void primes_initialBelow0_shouldReturnEmptyResponse() throws Exception {
        this.mockMvc.perform(get("/primes/-1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void primes_invalidAlgorithm_shouldReturn400() throws Exception {
        this.mockMvc.perform(get("/primes/10").param("algorithm", "notExisting"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void primes_initialNotSpecified_shouldReturn404() throws Exception {
        this.mockMvc.perform(get("/primes"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
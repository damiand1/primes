package com.dyl.prime.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Accessors(chain=true)
@JacksonXmlRootElement(localName = "PrimeSequenceResponse")
public class PrimeSequenceResponse {

    @JacksonXmlProperty(localName = "Initial")
    @JsonProperty("initial")
    private int initial;

    @JacksonXmlElementWrapper(localName = "Primes")
    @JacksonXmlProperty(localName = "Prime")
    @JsonProperty("primes")
    private List<Integer> primes;

}

# Damian Dyl Simple Prime Sequence Generator
## Introduction
Project created using Springboot Initializr.
## Technology
Project utilises Springboot 2.x.x and is built and
assembled using Maven. 

The project uses Springboot Web Starter and is supplied with
Embedded Tomcat/Jetty.

There is small number of dependencies including jackson xml
for marshalling java objects into xml responses.

There is also project lombok for removing boilerplate 
getters/setters/builders from POJO objects.

Finally I have decided to utilise hamcrest and mockito for 
composing unit and integration tests.

## Decisions justification
### Single DTO/Endpoint for both XML/JSON.
I have decided to use single class to represent both XML and JSON response.
The project is a very simple applicationa and for it's size single object and endpoint
can perfectly handle both formats. If the response would be more complicated
I would lean towards separate objects even if it would mean duplication of some of the code.

### Simplified cache for Sieve of Eratosthenes
Sieve of Eratosthenes is a sequential algorithm specifically designed to generate ranges
of prime numbers starting from the first prime number. I have decided that for this
algorithm we would only use cache if full range of requested numbers was already stored in cache.

In order to benefit from retrieving partial results from the cache and generating remaining
prime numbers I would have to develop a separate cache for Sieve of Eratosthenes that would
contain entire state of the algorithm i.e. memorising where we completed marking sieve for
each number in the sieve. For this exercise I decided to stick to a simple caching
solution.
### Concurrency
I have decided to use concurrency in order to improve prime number generation.
The concurrency is used in different ways depending on the algorithm.
For Brute force and Optimised BruteForce I split the range of numbers to be
checked into 2 and check each range in separate thread.

The concurrency is significantly more difficult to achieve in Sieve of
Eratosthenes as the algorithm is purely sequential and the real benefit
of it is only when generating full range of numbers. For that reason
I have only implemented concurrency in part of the algorithm which 
marks numbers as not primes in the sieve itself.


### Cache Implementation 
The cache is an extremely simple number storage based on LinkedHashSet.
The LinkedHashSet allows to store only unique numbers as well as maintaining order in which numbers were inserted.
Upon insertion the new numbers are sorted. This isn't really necessary but is a small precaution if someone inserts
numbers in wrong order.

Inappropriate usage of cache might break the application as algorithms assume that cache stores
prime numbers in ascending order and that there are missing prime numbers until certain range.
I didn't implement checking in the cache as I wanted to keep it very simple and basic.

The cache modification is asynchronous which means that springboot will first serve the request
and return generated prime numbers and only then update the cache. This is to improve the waiting time.

Finally the cache modification is synchronised which means that only single thread at the time can perform
modifications. This is to ensure that concurrency does not break the cache.

## Algorithms
### Brute Force
Simplest brute force algorithm that divides all numbers in range by all numbers from 2 to n where the n is the checked
number. 
### Optimised Brute Force
Optimised brute force algorithm. Does the same as Brute Force algorithm but only checks odd numbers and
only checks until square root of the actual number. 
### Sieve of Eratosthenes
Implementation of Sieve of Erathosthenes.

## Usage
First of all do mvn clean install
Run the PrimeApplication
Perform a REST GET request to localhost:8080/primes/{primeRange}
specify content-type/accept headers as application-xml to receive response in XML format
leave the content-type/accept headers empty or put application-json to receive json format response

Specify GET param "algorithm" in order to use different prime generation algorithms
The supported algorithms are:
* brute_force
* optimised_brute_force
* sieve
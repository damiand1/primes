package com.dyl.prime.testUtil;

import com.dyl.prime.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class PrimeAssertionUtils {

    public static List<Integer> getPrimesFor2() {
        return ListUtils.newList(2);
    }

    public static List<Integer> getPrimesFor3() {
        return ListUtils.newList(2, 3);
    }

    public static List<Integer> getPrimesFor5() {
        return ListUtils.newList(2, 3, 5);
    }

    public static List<Integer> getPrimesFor500() {
        return ListUtils.newList(
                2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67,
                71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163,
                167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263,
                269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373,
                379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479,
                487, 491, 499);
    }

    public static List<Integer> getPrimesFor1000() {
        return ListUtils.newList(
                2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67,
                71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163,
                167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263,
                269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373,
                379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479,
                487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601,
                607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719,
                727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839,
                853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971,
                977, 983, 991, 997);
    }

    public static List<Integer> getPrimesFor500to1000() {
        return ListUtils.newList(
                503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601,
                607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719,
                727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839,
                853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971,
                977, 983, 991, 997);
    }

    public static List<Integer> getPrimesFor1001() {
        return getPrimesFor1000();
    }

    public static List<Integer> getPrimeSequenceForRange(int range) {
        switch(range) {
            case 1:
                return new ArrayList<>();
            case 2:
                return getPrimesFor2();
            case 3:
                return getPrimesFor3();
            case 5:
                return getPrimesFor5();
            case 500:
                return getPrimesFor500();
            case 1000:
                return getPrimesFor1000();
            case 1001:
                return getPrimesFor1001();
            default:
                throw new IllegalArgumentException("Specified range is not supported");
        }
    }

}

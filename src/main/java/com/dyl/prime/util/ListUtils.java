package com.dyl.prime.util;

import java.util.Arrays;
import java.util.List;

public class ListUtils {
    public static <T> T getLastElement(List<T> list) {
        return list.get(list.size()-1);
    }

    public static <T> List<T> newList(T ...elements) {
        return Arrays.asList(elements);
    }
}

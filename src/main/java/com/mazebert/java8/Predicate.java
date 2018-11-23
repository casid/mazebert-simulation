package com.mazebert.java8;

// For android 19 compatibility
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}

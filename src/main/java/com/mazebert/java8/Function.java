package com.mazebert.java8;

// For android 19 compatibility
@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
}

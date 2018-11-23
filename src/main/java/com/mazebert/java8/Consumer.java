package com.mazebert.java8;

// For android 19 compatibility
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
}
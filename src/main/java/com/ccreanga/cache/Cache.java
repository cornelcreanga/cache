package com.ccreanga.cache;

public interface Cache<K, V> {

    void put(K key, V value);

    V get(K key);

    //added some extra methods
    boolean containsKey(K key);

    V remove(K key);

    boolean isEmpty();

    int size();

    void clear();
}

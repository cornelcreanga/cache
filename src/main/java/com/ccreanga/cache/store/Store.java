package com.ccreanga.cache.store;

import com.ccreanga.cache.CachedItem;

public interface Store<K, V> {

    void put(CachedItem<K, V> cachedItem);

    CachedItem<K, V> get(K key);

    CachedItem<K, V> remove(K key);

    boolean containsKey(K key);

    boolean isFull();

    boolean isEmpty();

    int size();

    void clear();


}

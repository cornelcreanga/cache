package com.ccreanga.cache.store;

import com.ccreanga.cache.CachedItem;
import com.ccreanga.cache.store.Store;

/**
 * not implemented yet
 *
 * @param <K>
 */
public class NonHeapMemoryStore<K, V> implements Store<K, V> {

    public NonHeapMemoryStore() {
        throw new RuntimeException("not yet implemented");
    }

    public void put(CachedItem cachedItem) {

    }

    public CachedItem<K, V> get(K key) {
        return null;
    }

    public CachedItem<K, V> remove(K key) {
        return null;
    }

    public boolean containsKey(K key) {
        return false;
    }

    public boolean isFull() {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    public int size() {
        return 0;
    }

    public void clear() {

    }
}

package com.ccreanga.cache.store;

import com.ccreanga.cache.CachedItem;
import com.google.common.base.Preconditions;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.HashMap;
@NotThreadSafe
public class HeapMemoryStore<K, V> implements Store<K, V> {

    private int maxItems;
    private int currentItems = 0;
    private static final int CAPACITY = 1000000001;
    public HashMap<K, CachedItem<K, V>> hashMap;


    public HeapMemoryStore(int maxItems) {
        hashMap = new HashMap<>();
        Preconditions.checkArgument((maxItems<=CAPACITY) && (maxItems>0));
        this.maxItems = maxItems;
    }


    public void put(CachedItem<K, V> o) {
        K key = o.getKey();
        if (!hashMap.containsKey(key)) {
            if (isFull())
                throw new StoreCapacityExceededException("store capacity exceeded,currentItems=" + currentItems);
            else currentItems++;
        }
        hashMap.put(key, o);

    }

    public CachedItem<K, V> get(K key) {
        return hashMap.get(key);
    }

    public CachedItem<K, V> remove(K key) {
        CachedItem<K, V> removed = hashMap.remove(key);
        if (removed != null)
            currentItems--;
        return removed;
    }

    public boolean containsKey(K key) {
        return hashMap.containsKey(key);
    }

    public boolean isFull() {
        return currentItems >= maxItems;
    }

    public boolean isEmpty() {
        return currentItems == 0;
    }

    public int size() {
        return currentItems;
    }

    public void clear() {
        hashMap.clear();
        currentItems = 0;
    }

}

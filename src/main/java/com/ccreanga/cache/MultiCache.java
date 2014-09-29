package com.ccreanga.cache;

import com.ccreanga.cache.store.DiskStore;
import com.ccreanga.cache.store.HeapMemoryStore;
import com.ccreanga.cache.store.Store;
import com.ccreanga.cache.strategy.Strategy;
import com.ccreanga.cache.strategy.StrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.PriorityQueue;

public class MultiCache<K, V> implements Cache<K, V> {

    private PriorityQueue<CachedItem<K, V>> queue;

    private Store<K, V> memoryStore;
    private Store<K, V> diskStore;

    static final Logger logger = LoggerFactory.getLogger(MultiCache.class);

    public MultiCache(int maxItemsMemory, int maxItemsDisk, String folder, Strategy strategy) {
        memoryStore = new HeapMemoryStore<>(maxItemsMemory);
        diskStore = new DiskStore<>(new File(folder), maxItemsDisk);
        queue = new PriorityQueue<>(128, StrategyFactory.getComparator(strategy));
    }

    public synchronized void put(K key, V value) {
        if (memoryStore.containsKey(key)) {
            CachedItem<K, V> item = memoryStore.remove(key);
            queue.remove(item);
        } else if (diskStore.containsKey(key)) {
            CachedItem<K, V> item = diskStore.remove(key);
            queue.remove(item);
        }

        CachedItem<K, V> item = new CachedItem<>(key, value);
        //check for space in memory
        if (!memoryStore.isFull()) {
            memoryStore.put(item);
        } else if (!diskStore.isFull()) {
            diskStore.put(item);
        } else {
            //evict items
            CachedItem<K, V> toBeRemoved = queue.remove();
            K keyToBeRemoved = toBeRemoved.getKey();
            if (memoryStore.containsKey(keyToBeRemoved)) {
                memoryStore.remove(keyToBeRemoved);
                memoryStore.put(item);
            } else {
                diskStore.remove(keyToBeRemoved);
                diskStore.put(item);
            }
        }
        queue.add(item);

    }

    public synchronized V get(K key) {
        if (memoryStore.containsKey(key)) {
            CachedItem<K, V> item = memoryStore.get(key);
            item.increaseHitCount();
            item.setLastAccessedTime(System.currentTimeMillis());
            queue.remove(item);
            queue.add(item);
            return item.getValue();
        } else if (diskStore.containsKey(key)) {
            CachedItem<K, V> item = diskStore.get(key);
            item.increaseHitCount();
            item.setLastAccessedTime(System.currentTimeMillis());
            queue.remove(item);
            queue.add(item);
            return item.getValue();
        } else return null;
    }

    public synchronized boolean containsKey(K key) {
        return memoryStore.containsKey(key) || diskStore.containsKey(key);
    }

    public synchronized V remove(K key) {
        if (memoryStore.containsKey(key)) {
            CachedItem<K, V> item = memoryStore.remove(key);
            queue.remove(item);

        } else if (diskStore.containsKey(key)) {
            CachedItem<K, V> item = diskStore.remove(key);
            queue.remove(item);
        }
        return null;
    }

    public synchronized boolean isEmpty() {
        return memoryStore.isEmpty() && diskStore.isEmpty();
    }

    public synchronized int size() {
        return memoryStore.size() + diskStore.size();
    }

    public synchronized void clear() {
        memoryStore.clear();
        diskStore.clear();
    }

}

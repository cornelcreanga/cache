package com.ccreanga.cache;

import java.io.Serializable;

public class CachedItem<K, V> implements Serializable {
    K key;
    V value;

    long creationTime;
    long lastAccessedTime;
    long hitCount;

    public CachedItem(K key, V value) {
        this.key = key;
        this.value = value;
        creationTime = System.currentTimeMillis();
        lastAccessedTime = -1;
        hitCount = 0;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    public long getHitCount() {
        return hitCount;
    }

    public void increaseHitCount() {
        hitCount++;
    }
}



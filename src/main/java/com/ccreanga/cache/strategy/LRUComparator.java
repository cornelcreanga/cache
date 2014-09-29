package com.ccreanga.cache.strategy;

import com.ccreanga.cache.CachedItem;

import java.util.Comparator;

public class LRUComparator implements Comparator<CachedItem> {

    public int compare(CachedItem first, CachedItem second) {
        long x = first.getLastAccessedTime();
        long y = second.getLastAccessedTime();
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }
}

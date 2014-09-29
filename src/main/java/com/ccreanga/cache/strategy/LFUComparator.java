package com.ccreanga.cache.strategy;

import com.ccreanga.cache.CachedItem;

import java.util.Comparator;

public class LFUComparator implements Comparator<CachedItem> {

    public int compare(CachedItem first, CachedItem second) {
        long x = first.getHitCount();
        long y = second.getHitCount();
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

}

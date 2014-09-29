package com.ccreanga.cache.strategy;

import com.ccreanga.cache.CachedItem;

import java.util.Comparator;

public class FIFOComparator implements Comparator<CachedItem> {

    public int compare(CachedItem first, CachedItem second) {
        long x = first.getCreationTime();
        long y = second.getCreationTime();
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }
}

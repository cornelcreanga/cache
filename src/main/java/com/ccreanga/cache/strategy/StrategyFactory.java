package com.ccreanga.cache.strategy;

import com.ccreanga.cache.CachedItem;

import java.util.Comparator;

public class StrategyFactory {

    public static Comparator<CachedItem> getComparator(Strategy strategy) {
        if (strategy == Strategy.FIFO)
            return new FIFOComparator();
        if (strategy == Strategy.LRU)
            return new LRUComparator();
        if (strategy == Strategy.LFU)
            return new LFUComparator();
        throw new RuntimeException("illegal strategy");
    }
}

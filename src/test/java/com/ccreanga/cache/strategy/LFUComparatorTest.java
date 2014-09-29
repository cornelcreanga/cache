package com.ccreanga.cache.strategy;

import com.ccreanga.cache.CachedItem;
import org.junit.Test;

import static org.junit.Assert.*;

public class LFUComparatorTest {

    @Test
    public void testCompareEquals() throws Exception {
        LFUComparator lfuComparator = new LFUComparator();
        CachedItem<String, String> first = new CachedItem<>("key","value");
        first.increaseHitCount();
        CachedItem<String, String> second = new CachedItem<>("key","value");
        second.increaseHitCount();
        assertTrue(lfuComparator.compare(first,second)==0);
    }

    @Test
    public void testCompareGreater() throws Exception {
        LFUComparator lfuComparator = new LFUComparator();
        CachedItem<String, String> first = new CachedItem<>("key","value");
        first.increaseHitCount();first.increaseHitCount();
        CachedItem<String, String> second = new CachedItem<>("key","value");
        second.increaseHitCount();
        assertTrue(lfuComparator.compare(first,second)==1);
    }

    @Test
    public void testCompareLower() throws Exception {
        LFUComparator lfuComparator = new LFUComparator();
        CachedItem<String, String> first = new CachedItem<>("key","value");
        first.increaseHitCount();
        CachedItem<String, String> second = new CachedItem<>("key","value");
        second.increaseHitCount();second.increaseHitCount();
        assertTrue(lfuComparator.compare(first,second)==-1);
    }
}
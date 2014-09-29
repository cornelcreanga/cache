package com.ccreanga.cache.strategy;

import com.ccreanga.cache.CachedItem;
import org.junit.Test;

import static org.junit.Assert.*;

public class LRUComparatorTest {

    @Test
    public void testCompareEquals() throws Exception {
        LRUComparator lruComparator = new LRUComparator();
        CachedItem<String, String> first = new CachedItem<>("key","value");
        first.setLastAccessedTime(1);
        CachedItem<String, String> second = new CachedItem<>("key","value");
        second.setLastAccessedTime(1);
        assertTrue(lruComparator.compare(first,second)==0);
    }

    @Test
    public void testCompareGreater() throws Exception {
        LRUComparator lruComparator = new LRUComparator();
        CachedItem<String, String> first = new CachedItem<>("key","value");
        first.setLastAccessedTime(2);
        CachedItem<String, String> second = new CachedItem<>("key","value");
        second.setLastAccessedTime(1);
        assertTrue(lruComparator.compare(first,second)==1);
    }

    @Test
    public void testCompareLower() throws Exception {
        LRUComparator lruComparator = new LRUComparator();
        CachedItem<String, String> first = new CachedItem<>("key","value");
        first.setLastAccessedTime(1);
        CachedItem<String, String> second = new CachedItem<>("key","value");
        second.setLastAccessedTime(2);
        assertTrue(lruComparator.compare(first,second)==-1);
    }
}
package com.ccreanga.cache.strategy;

import com.ccreanga.cache.CachedItem;
import org.junit.Test;

import static org.junit.Assert.*;

public class FIFOComparatorTest {

    @Test
    public void testCompareEquals() throws Exception {
        FIFOComparator fifoComparator = new FIFOComparator();
        CachedItem<String, String> first = new CachedItem<>("key","value");
        first.setCreationTime(1);
        CachedItem<String, String> second = new CachedItem<>("key","value");
        second.setCreationTime(1);
        assertTrue(fifoComparator.compare(first,second)==0);
    }

    @Test
    public void testCompareGreater() throws Exception {
        FIFOComparator fifoComparator = new FIFOComparator();
        CachedItem<String, String> first = new CachedItem<>("key","value");
        first.setCreationTime(2);
        CachedItem<String, String> second = new CachedItem<>("key","value");
        second.setCreationTime(1);
        assertTrue(fifoComparator.compare(first,second)==1);
    }

    @Test
    public void testCompareLower() throws Exception {
        FIFOComparator fifoComparator = new FIFOComparator();
        CachedItem<String, String> first = new CachedItem<>("key","value");
        first.setCreationTime(1);
        CachedItem<String, String> second = new CachedItem<>("key","value");
        second.setCreationTime(2);
        assertTrue(fifoComparator.compare(first,second)==-1);
    }

}
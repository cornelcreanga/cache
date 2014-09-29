package com.ccreanga.cache;

import com.ccreanga.cache.strategy.Strategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MultiCacheStrategyTest {

    MultiCache<String,String> cache;
    static int MAX_MEMORY = 2;
    static int MAX_DISK=2;
    static String FOLDER = "/tmp";


    @Test
    public void testFIFO(){
        cache = new MultiCache<>(MAX_MEMORY,MAX_DISK,FOLDER, Strategy.FIFO);
        cache.put("1","1");
        cache.put("2","2");
        cache.put("3","3");
        cache.put("4","4");
        cache.put("5","5");
        assertFalse(cache.containsKey("1"));
        assertTrue(cache.containsKey("2"));
        assertTrue(cache.containsKey("3"));
        assertTrue(cache.containsKey("4"));
        assertTrue(cache.containsKey("5"));
    }

    @Test
    public void testLRU(){
        cache = new MultiCache<>(MAX_MEMORY,MAX_DISK,FOLDER, Strategy.LRU);
        cache.put("1","1");
        cache.put("2","2");
        cache.put("3","3");
        cache.put("4","4");
        cache.get("2");
        cache.get("3");
        cache.get("4");
        cache.put("5","5");
        assertFalse(cache.containsKey("1"));
        assertTrue(cache.containsKey("2"));
        assertTrue(cache.containsKey("3"));
        assertTrue(cache.containsKey("4"));
        assertTrue(cache.containsKey("5"));

    }

    @Test
    public void testLFU(){
        cache = new MultiCache<>(MAX_MEMORY,MAX_DISK,FOLDER, Strategy.LFU);
        cache.put("1","1");
        cache.put("2","2");
        cache.put("3","3");
        cache.put("4","4");

        cache.get("1");cache.get("1");
        cache.get("2");cache.get("2");
        cache.get("3");cache.get("3");
        cache.get("4");

        cache.put("5","5");
        assertTrue(cache.containsKey("1"));
        assertTrue(cache.containsKey("2"));
        assertTrue(cache.containsKey("3"));
        assertFalse(cache.containsKey("4"));
        assertTrue(cache.containsKey("5"));

    }


}

package com.ccreanga.cache;

import com.ccreanga.cache.strategy.Strategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class MultiCacheTest {

    MultiCache<String,String> cache;
    static int MAX_MEMORY = 2;
    static int MAX_DISK=2;
    static String FOLDER = "/tmp";

    @Before
    public void setUp() {
        cache = new MultiCache<>(MAX_MEMORY,MAX_DISK,FOLDER, Strategy.FIFO);
    }

    @Test
    public void testPut() throws Exception {
        cache.put("key","value");
        assertTrue(cache.containsKey("key"));
    }

    @Test
    public void testGet() throws Exception {
        cache.put("key","value");
        String value = cache.get("key");
        assertEquals(value,"value");
    }

    @Test
    public void testContainsKey() throws Exception {
        cache.put("key","value");
        assertTrue(cache.containsKey("key"));
        assertFalse(cache.containsKey("key____"));

    }

    @Test
    public void testRemove() throws Exception {
        cache.put("key","value");
        assertTrue(cache.containsKey("key"));
        cache.remove("key");
        assertFalse(cache.containsKey("key"));
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(cache.isEmpty());
        cache.put("key","value");
        assertFalse(cache.isEmpty());
        cache.remove("key");
        assertTrue(cache.isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        assertTrue(cache.size()==0);
        cache.put("key","value");
        assertTrue(cache.size()==1);
        cache.remove("key");
        assertTrue(cache.size()==0);
        cache.put("key","value");
        cache.put("key","value");
        assertTrue(cache.size()==1);
    }

    @Test
    public void testClear() throws Exception {
        cache.put("key","value");
        cache.clear();
        assertTrue(cache.isEmpty());
    }
}
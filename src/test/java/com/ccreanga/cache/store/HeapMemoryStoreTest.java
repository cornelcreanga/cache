package com.ccreanga.cache.store;

import com.ccreanga.cache.CachedItem;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HeapMemoryStoreTest {

    private File root;
    private HeapMemoryStore<String,Date> map;
    public static final int MAX_ITEMS = 10;

    public HeapMemoryStoreTest() {
        root = new File("/tmp/persistent_test");
    }

    @Before
    public void setUp() throws IOException {
        FileUtils.deleteDirectory(root);
        map = new HeapMemoryStore<>(MAX_ITEMS);
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(root);
    }

    @Test
    public void testContainsKey() {
        assertFalse(map.containsKey("key"));
        map.put(new CachedItem<>("key", new Date()));
        assertTrue(map.containsKey("key"));
        assertFalse(map.containsKey("fail"));
    }


    @Test
    public void testGet() {
        Date value = new Date();
        map.put(new CachedItem<>("key", value));
        assertEquals(value, map.get("key").getValue());
    }

    @Test
    public void testPut() {
        Date value = new Date();
        assertFalse(map.containsKey("key"));
        map.put(new CachedItem<>("key", value));
        assertTrue(map.containsKey("key"));
    }

    @Test
    public void testExistingPut() {
        Date value = new Date();
        assertFalse(map.containsKey("key"));
        map.put(new CachedItem<>("key", value));
        assertTrue(map.containsKey("key"));
        map.put(new CachedItem<>("key", value));
        assertTrue(map.containsKey("key"));

    }

    @Test
    public void testRemove() {
        Date value = new Date();
        map.put(new CachedItem<>("key", value));
        assertTrue(map.containsKey("key"));
        map.remove("key");
        assertFalse(map.containsKey("key"));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(map.isEmpty());
        map.put(new CachedItem<>("key", new Date()));
        assertFalse(map.isEmpty());
        map.remove("key");
        assertTrue(map.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(0, map.size());
        map.put(new CachedItem<>("test", new Date()));
        map.put(new CachedItem<>("test1", new Date()));
        assertEquals(2, map.size());
        map.put(new CachedItem<>("test1", new Date()));
        assertEquals(2, map.size());
        map.remove("test4");
        assertEquals(2, map.size());
        map.remove("test1");
        assertEquals(1, map.size());

    }

    @Test
    public void testClear() throws IOException {
        Date value = new Date();
        map.put(new CachedItem<>("key", value));
        assertTrue(map.containsKey("key"));
        map.clear();
        assertFalse(map.containsKey("key"));
        assertTrue(map.isEmpty());
    }

    @Test
    public void testFull() throws Exception {
        for (int i = 0; i < MAX_ITEMS; i++) {
            map.put(new CachedItem<>("key"+i, new Date()));
        }
        assertTrue(map.isFull());
    }

}

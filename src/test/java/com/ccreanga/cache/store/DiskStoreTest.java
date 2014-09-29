package com.ccreanga.cache.store;

import com.ccreanga.cache.CachedItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

public class DiskStoreTest {

    public static final int MAX_ITEMS = 10;
    private File root;
    private DiskStore<String,Date> store;

    public DiskStoreTest() {
        root = new File("/tmp");
    }

    @Before
    public void setUp() throws IOException {
        store = new DiskStore<>(root, MAX_ITEMS);
    }

    @After
    public void tearDown() throws IOException {
        store.clear();
    }

    @Test
    public void testContainsKey() {
        assertFalse(store.containsKey("key"));
        store.put(new CachedItem<>("key", new Date()));
        assertTrue(store.containsKey("key"));
        assertFalse(store.containsKey("fail"));
    }


    @Test
    public void testGet() {
        Date value = new Date();
        store.put(new CachedItem<>("key", value));
        assertEquals(value, store.get("key").getValue());
    }

    @Test
    public void testPut() {
        Date value = new Date();
        assertFalse(store.containsKey("key"));
        store.put(new CachedItem<>("key", value));
        assertTrue(store.containsKey("key"));
    }

    @Test
    public void testExistingPut() {
        Date value = new Date();
        assertFalse(store.containsKey("key"));
        store.put(new CachedItem<>("key", value));
        assertTrue(store.containsKey("key"));
        store.put(new CachedItem<>("key", value));
        assertTrue(store.containsKey("key"));

    }

    @Test
    public void testRemove() {
        Date value = new Date();
        store.put(new CachedItem<>("key", value));
        assertTrue(store.containsKey("key"));
        store.remove("key");
        assertFalse(store.containsKey("key"));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(store.isEmpty());
        store.put(new CachedItem<>("key", new Date()));
        assertFalse(store.isEmpty());
        store.remove("key");
        assertTrue(store.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(0, store.size());
        store.put(new CachedItem<>("test", new Date()));
        store.put(new CachedItem<>("test1", new Date()));
        assertEquals(2, store.size());
        store.put(new CachedItem<>("test1", new Date()));
        assertEquals(2, store.size());
        store.remove("test4");
        assertEquals(2, store.size());
        store.remove("test1");
        assertEquals(1, store.size());

    }

    @Test
    public void testClear() throws IOException {
        Date value = new Date();
        store.put(new CachedItem<>("key", value));
        assertTrue(store.containsKey("key"));
        store.clear();
        assertFalse(store.containsKey("key"));
        assertTrue(store.isEmpty());
    }

    @Test
    public void testFull() throws Exception {
        for (int i = 0; i < MAX_ITEMS; i++) {
            store.put(new CachedItem<>("key" + i, new Date()));
        }
        assertTrue(store.isFull());
    }
}
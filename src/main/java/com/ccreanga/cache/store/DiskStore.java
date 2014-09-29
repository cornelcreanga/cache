package com.ccreanga.cache.store;


import com.ccreanga.cache.CacheCreationException;
import com.ccreanga.cache.CacheReadException;
import com.ccreanga.cache.CacheWriteException;
import com.ccreanga.cache.CachedItem;
import com.ccreanga.cache.serializers.JDKSerializer;
import com.ccreanga.cache.serializers.ObjectSerializer;
import com.google.common.base.Preconditions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;

public class DiskStore<K, V> implements Store<K, V> {

    private File root;
    private File keysFolder;
    private File valuesFolder;
    private ObjectSerializer serializer;
    private static final int CAPACITY = 1000000001;
    private int currentItems = 0;
    private int maxItems;


    public DiskStore(File root, int maxItems) {
        this.root = Preconditions.checkNotNull(root);
        Preconditions.checkArgument((maxItems<=CAPACITY) && (maxItems>0));
        this.maxItems = maxItems;
        this.serializer = new JDKSerializer();

        if ((!root.exists()) && (!root.mkdir()))
            throw new CacheCreationException("cannot create root folder");


        keysFolder = new File(root, "keys");
        valuesFolder = new File(root, "values");

        try {
            FileUtils.deleteDirectory(keysFolder);
            FileUtils.deleteDirectory(valuesFolder);
        } catch (IOException e) {
            throw new CacheCreationException(e);
        }

        mkdirs(keysFolder);
        mkdirs(valuesFolder);

    }

    public boolean containsKey(K key) {
        try {
            return initFileName(key, false) != null;
        } catch (IOException ioe) {
            return false;
        }
    }

    @Override
    public boolean isFull() {
        return currentItems >= maxItems;
    }

    public CachedItem<K, V> get(K key) {
        try {
            String fileName = initFileName(key, false);
            if (fileName == null)
                return null;
            File valueFile = new File(valuesFolder, fileName);
            return readValueObject(valueFile);
        } catch (IOException ioe) {
            return null;
        }
    }

    public void put(CachedItem<K, V> value) {
        try {
            K key = value.getKey();

            if (!containsKey(key)) {
                if (isFull())
                    throw new StoreCapacityExceededException("store capacity exceeded,currentItems=" + currentItems);
                else currentItems++;
            }

            String fileName = initFileName(key, true);
            File keyFile = new File(keysFolder, fileName);
            File valueFile = new File(valuesFolder, fileName);
            writeObject(keyFile, key);
            writeObject(valueFile, value);
        } catch (IOException ioe) {
            currentItems--;
        }
    }

    public CachedItem<K, V> remove(K key) {
        try {
            String fileName = initFileName(key, false);
            if (fileName == null)//not found
                return null;
            File keyFile = new File(keysFolder, fileName);
            File valueFile = new File(valuesFolder, fileName);
            CachedItem<K, V> oldValue = readValueObject(valueFile);
            delete(keyFile);
            delete(valueFile);

            deleteEmptyFolders(keysFolder,keyFile);
            deleteEmptyFolders(valuesFolder,valueFile);

            currentItems--;
            return oldValue;
        } catch (IOException ioe) {
            return null;
        }
    }


    private void deleteEmptyFolders(File root, File child){
        while(true){
            File parent = child.getParentFile();
            if (parent.equals(root))
                break;
            File[] listFiles=parent.listFiles();
            if (((listFiles!=null)) && (listFiles.length==0))
                delete(parent);
            child = parent;
        }
    }

    public boolean isEmpty() {
        return currentItems == 0;
    }

    public int size() {
        return currentItems;
    }

    public void clear() {
        try {
            FileUtils.deleteDirectory(keysFolder);
            FileUtils.deleteDirectory(valuesFolder);
        } catch (IOException e) {
            throw new CacheWriteException(e);
        }
        currentItems = 0;
    }

    protected String initFileName(K key, boolean create) throws IOException {
        String fileName = obtainFileName(key);
        File keyFile;
        String dirName = fileName.substring(0, 9);
        File keyDir = new File(keysFolder, dirName);
        File valueDir = new File(valuesFolder, dirName);
        if (create) {
            keyDir.mkdirs();
            valueDir.mkdirs();
        } else if (!keyDir.isDirectory() || !valueDir.isDirectory())
            return null;
        String fileNameI;
        for (int i = 0; true; i++) {
            fileNameI = fileName + i;
            keyFile = new File(keysFolder, fileNameI);
            if (!keyFile.exists()) {
                if (!create)
                    return null;
                else
                    break;
            }
            K candidateKey = readKeyObject(keyFile);
            if (key.equals(candidateKey))
                break;
        }
        return fileNameI;
    }

    protected String obtainFileName(K key) {
        String fileName = StringUtils.leftPad("" + (key.hashCode() & 0x7FFFFFFF) % CAPACITY, 9, '0');
        return fileName.substring(7) + File.separator +
                fileName.substring(5, 7) + File.separator +
                fileName.substring(3, 5) + File.separator +
                fileName.substring(0, 3);
    }

    private K readKeyObject(File file) {
        try {
            try (FileInputStream in = new FileInputStream(file)) {
                return (K) serializer.deserialize(in);
            }
        } catch (IOException e) {
            throw new CacheReadException(e);
        }
    }

    private CachedItem<K, V> readValueObject(File file) {
        try {
            try (FileInputStream in = new FileInputStream(file)) {
                return (CachedItem<K, V>) serializer.deserialize(in);
            }
        } catch (IOException e) {
            throw new CacheReadException(e);
        }
    }


    private void writeObject(File file, Object value) {
        try {
            try (FileOutputStream out = new FileOutputStream(file)) {
                serializer.serialize(value, out);
            }
        } catch (IOException e) {
            throw new CacheWriteException(e);
        }
    }

    private void delete(File file){
        try{
            Files.delete(file.toPath());
        }catch(IOException e){
            throw new CacheWriteException(e);
        }
    }


    private void mkdirs(File file){
        if (!file.mkdirs())
            throw new CacheWriteException("cannot create folders:"+file.toPath());
    }

}

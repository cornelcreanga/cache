package com.ccreanga.cache.serializers;


import java.io.*;

public class JDKSerializer<T> implements ObjectSerializer<T> {


    public void serialize(T value, OutputStream out) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(value);
        }
    }

    public T deserialize(InputStream in) throws IOException {

        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            try {
                return (T) ois.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

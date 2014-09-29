package com.ccreanga.cache.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.InputStream;
import java.io.OutputStream;

public class KryoSerializer<T> implements ObjectSerializer<T> {

    private Class<T> type;

    public KryoSerializer(Class<T> type) {
        this.type = type;
    }

    public void serialize(T value, OutputStream out) {
        Kryo kryo = new Kryo();
        try (Output output = new Output(out)) {
            kryo.writeObject(output, value);
        }

    }

    public T deserialize(InputStream in) {
        Kryo kryo = new Kryo();
        try (Input input = new Input(in)) {
            return kryo.readObject(input, type);
        }

    }


}

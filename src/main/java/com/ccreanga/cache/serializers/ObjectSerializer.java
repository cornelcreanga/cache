package com.ccreanga.cache.serializers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ObjectSerializer<T> {

    void serialize(T value, OutputStream out) throws IOException;

    T deserialize(InputStream in) throws IOException;


}

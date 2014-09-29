package com.ccreanga.cache.serializers;

import com.ccreanga.cache.serializers.KryoSerializer;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

public class KryoSerializerTest {

    @Test
    public void testSerialization() throws Exception {
        String test="test2";
        KryoSerializer<String> kryoSerializer = new KryoSerializer<String>(String.class);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        kryoSerializer.serialize(test,baos);

        String toTest = kryoSerializer.deserialize(new ByteArrayInputStream(baos.toByteArray()));
        assertEquals(test,toTest);
    }

}
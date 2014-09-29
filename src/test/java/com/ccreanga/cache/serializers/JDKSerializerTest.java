package com.ccreanga.cache.serializers;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

public class JDKSerializerTest {

    @Test
    public void testSerialization() throws Exception {
        String test="test2";
        JDKSerializer jdkSerializer = new JDKSerializer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        jdkSerializer.serialize(test, baos);

        String toTest = (String) jdkSerializer.deserialize(new ByteArrayInputStream(baos.toByteArray()));
        assertEquals(test,toTest);
    }

}

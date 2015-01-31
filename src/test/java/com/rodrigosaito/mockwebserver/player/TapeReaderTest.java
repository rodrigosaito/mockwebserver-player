package com.rodrigosaito.mockwebserver.player;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TapeReaderTest {

    private TapeReader reader = new TapeReader();

    @Test
    public void testRead() throws Exception {
        Tape tape = reader.read("simple_play");

        assertEquals("simple play", tape.getName());
    }
}
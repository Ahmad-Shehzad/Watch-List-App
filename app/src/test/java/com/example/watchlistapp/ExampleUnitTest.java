package com.example.watchlistapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void isAhmadBad() {
        final String Ahmad = "bad";

        assertEquals(Ahmad.toString(), "bad");
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}
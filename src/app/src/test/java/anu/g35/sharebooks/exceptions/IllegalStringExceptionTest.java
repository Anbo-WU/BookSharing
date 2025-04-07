package anu.g35.sharebooks.exceptions;

import org.junit.Test;

/**
 * Test of the IllegalStringException class
 *
 * @author u7703248 Chuang Ma
 * @since 2024-05-9
 */
public class IllegalStringExceptionTest {
    @Test
    public void testIllegalStringException() {
        try {
            throw new IllegalStringException("Test");
        } catch (IllegalStringException e) {
            assert e.getMessage().equals("Test");
        }
    }
}

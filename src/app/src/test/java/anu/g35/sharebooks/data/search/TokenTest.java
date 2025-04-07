package anu.g35.sharebooks.data.search;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test of the Token class
 *
 * @author Diao Fu
 * @since 2024-05-9
 */
public class TokenTest {

    @Test
    public void testToken() {
        Token token = new Token("a title", Token.Type.TITLE);
        assertEquals("a title", token.getToken());
        assertEquals(Token.Type.TITLE, token.getType());
        // toString() method is not implemented in Token class
        assertEquals("Token{token='a title', type=TITLE}", token.toString());
    }
}

package anu.g35.sharebooks.data.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * Test of the Tokenizer class
 *
 * @author Diao Fu
 * @since 2024-05-9
 */
public class TokenizerTest {

    @Test
    public void testTokenizer() {
        Tokenizer tokenizer = new Tokenizer("TITLE:a title&AUTHORS:an author&YEAR:2021|CATEGORY:category|ISBN:1234567890123");

        if (tokenizer.hasNext()) {
            Token token = tokenizer.current();
            assertEquals("TITLE", token.getType().getValue());
            assertEquals("a title", token.getToken());
        }

        tokenizer.next();
        if (tokenizer.hasNext()) {
            Token token = tokenizer.current();
            assertEquals("&", token.getType().getValue());
            assertEquals("&", token.getToken());
        }

        tokenizer.next();
        if (tokenizer.hasNext()) {
            Token token = tokenizer.current();
            assertEquals("AUTHORS", token.getType().getValue());
            assertEquals("an author", token.getToken());
        }

        tokenizer.next();
        if (tokenizer.hasNext()) {
            Token token = tokenizer.current();
            assertEquals("&", token.getType().getValue());
            assertEquals("&", token.getToken());
        }

        tokenizer.next();
        if (tokenizer.hasNext()) {
            Token token = tokenizer.current();
            assertEquals("YEAR", token.getType().getValue());
            assertEquals("2021", token.getToken());
        }

        tokenizer.next();
        if (tokenizer.hasNext()) {
            Token token = tokenizer.current();
            assertEquals("|", token.getType().getValue());
            assertEquals("|", token.getToken());
        }

        tokenizer.next();
        if (tokenizer.hasNext()) {
            Token token = tokenizer.current();
            assertEquals("CATEGORY", token.getType().getValue());
            assertEquals("category", token.getToken());
        }

        tokenizer.next();
        if (tokenizer.hasNext()) {
            Token token = tokenizer.current();
            assertEquals("|", token.getType().getValue());
            assertEquals("|", token.getToken());
        }

        tokenizer.next();
        if (tokenizer.hasNext()) {
            Token token = tokenizer.current();
            assertEquals("ISBN", token.getType().getValue());
            assertEquals("1234567890123", token.getToken());
        }
    }

    @Test
    public void testTokenizerInvalid() {
        Tokenizer tokenizer = new Tokenizer("1234567890123");
        Token token = tokenizer.current();
        assertEquals("ISBN", token.getType().getValue());
        assertEquals("1234567890123", token.getToken());

        tokenizer = new Tokenizer("2021");
        token = tokenizer.current();
        assertEquals("YEAR", token.getType().getValue());
        assertEquals("2021", token.getToken());


        tokenizer = new Tokenizer("white");
        token = tokenizer.current();
        assertEquals("TITLE", token.getType().getValue());
        assertEquals("white", token.getToken());
        tokenizer.next();
        tokenizer.next();
        token = tokenizer.current();
        assertEquals("AUTHORS", token.getType().getValue());
        assertEquals("white", token.getToken());

        tokenizer = new Tokenizer("");
        assertEquals(true, tokenizer.current() == null);


        tokenizer = new Tokenizer("AAAA:white");
        token = tokenizer.current();
        assertEquals("TITLE", token.getType().getValue());
        assertEquals("AAAA white", token.getToken());

    }
}

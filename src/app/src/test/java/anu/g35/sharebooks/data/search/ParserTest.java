package anu.g35.sharebooks.data.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


/**
 * Test of the Parser class
 *
 * @author Diao Fu
 * @since 2024-05-9
 */
@RunWith(RobolectricTestRunner.class)
public class ParserTest {

    @Test
    public void testParser() {
        Tokenizer tokenizer = new Tokenizer("TITLE:a title&AUTHORS:an author&YEAR:2021|(CATEGORY:category|ISBN:1234567890123)");
        Parser parser = new Parser(tokenizer);
        Expression expression = parser.buildExp();
        assertEquals("(TITLE:a title AND (AUTHORS:an author AND (YEAR:2021 OR (CATEGORY:category OR ISBN:1234567890123))))", expression.show());
    }

}

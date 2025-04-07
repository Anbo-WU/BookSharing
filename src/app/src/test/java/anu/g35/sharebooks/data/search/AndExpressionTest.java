package anu.g35.sharebooks.data.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

/**
 * Test of the AndExpression class
 *
 * @author Diao Fu
 * @since 2024-05-9
 */
@RunWith(RobolectricTestRunner.class)
public class AndExpressionTest {

    @Test
    public void testAnd() {
        Token token1 = new Token("a title", Token.Type.TITLE);
        Token token2 = new Token("authors", Token.Type.AUTHORS);
        Expression key1 = new KeyExpression(token1);
        Expression key2 = new KeyExpression(token2);
        Expression expression = new AndExpression(key1, key2);
        assertEquals("(TITLE:a title AND AUTHORS:authors)", expression.show());
        assertEquals(true, expression.evaluate() != null);
    }

}

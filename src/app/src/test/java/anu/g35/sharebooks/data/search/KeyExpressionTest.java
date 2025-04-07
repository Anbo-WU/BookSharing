package anu.g35.sharebooks.data.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

/**
 * Test of the KeyExpression class
 *
 * @author Diao Fu
 * @since 2024-05-9
 */
@RunWith(RobolectricTestRunner.class)
public class KeyExpressionTest {

        @Test
        public void testKeyExpression() {
            Token token = new Token("a title", Token.Type.TITLE);
            Expression expression = new KeyExpression(token);
            assertEquals("TITLE:a title", expression.show());
            assertEquals(true, expression.evaluate() != null);
        }


}

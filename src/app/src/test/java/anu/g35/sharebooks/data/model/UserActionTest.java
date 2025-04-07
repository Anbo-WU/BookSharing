package anu.g35.sharebooks.data.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.time.LocalDateTime;


/**
 * Test of the UserAction class
 *
 * @author u7703248 Chuang Ma
 * @since 2024-05-9
 */
public class UserActionTest {

        @Test
        public void testNewUserAction() {
            UserAction userAction = new UserAction();

            userAction.setUserId("id");
            userAction.setAtBookISBN(1234567890L);
            userAction.setActionType(UserAction.Type.BORROW);
            LocalDateTime now = LocalDateTime.now();
            userAction.setTimestamp(now);
            userAction.setContent("content");
            userAction.setAtUserId("atUserId");

            assertEquals("id", userAction.getUserId());
            assertEquals(1234567890L, userAction.getAtBookISBN().longValue());
            assertEquals(UserAction.Type.BORROW, userAction.getActionType());
            assertEquals(now, userAction.getTimestamp());
            assertEquals("content", userAction.getContent());
            assertEquals("atUserId", userAction.getAtUserId());
            assertEquals(true,userAction.toString() != null);

        }
}

package anu.g35.sharebooks.data.session;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import anu.g35.sharebooks.data.model.Result;
import anu.g35.sharebooks.data.model.User;


/**
 * Test of the LoginState class
 *
 * @author u7703248 Chuang Ma
 * @since 2024-05-9
 */
@RunWith(RobolectricTestRunner.class)
public class LoginStateTest {

    @Test
    public void testOtherAction(){
        UserSession userSession = UserSession.getInstance();
        if (!userSession.isLogin()) {
            Result<User> result = userSession.login("test@t.t", "123456");
            assert (result instanceof Result.Success);
        }

        UserState state = userSession.getUserState();

        assertEquals(true, userSession.borrowBook(9780007179817L));
        assertEquals(true, state.returnBook(9780007179817L ));
        assertEquals(null, state.login("test@t.taaa", "123456"));

        assertEquals(true, userSession.likeBook(9780006496434L, true));
        assertEquals(true, userSession.likeBook(9780006496434L, false));

        assertEquals(true, userSession.borrowBook(9780060004507L));
        assertEquals(true, userSession.returnBook(9780060004507L));


        assertEquals(true, userSession.followUser("ChuangMa@anu.edu.au", true));
        assertEquals(true, userSession.followUser("ChuangMa@anu.edu.au", false));

    }

}

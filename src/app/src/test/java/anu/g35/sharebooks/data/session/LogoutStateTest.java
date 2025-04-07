package anu.g35.sharebooks.data.session;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import anu.g35.sharebooks.data.model.Result;
import anu.g35.sharebooks.data.model.User;

/**
 * Test of the LogoutState class
 *
 * @author u7703248 Chuang Ma
 * @since 2024-05-9
 */
@RunWith(RobolectricTestRunner.class)
public class LogoutStateTest {


    @Test
    public void testActionAtLogout(){
        UserSession userSession = UserSession.getInstance();

        if (userSession.isLogin()) {
            boolean result = userSession.logout();
            assert (result);
        }

        UserState state = userSession.getUserState();
        // assert (state instanceof LogoutState);

        assertEquals(false, state.borrowBook(9780006496434L));
        assertEquals(false, state.borrowBook(9780006496434L, "test@t.t"));
        assertEquals(false, state.likeBook(9780006496434L, true));
        assertEquals(false, state.likeBook(9780006496434L, false, "test@t.t"));
        assertEquals(false, state.followUser("test@t.t", true));
        assertEquals(false, state.followUser("test@t.t", false, "test@t.t"));
        assertEquals(false, state.returnBook(9780006496434L));
        assertEquals(false, state.returnBook(9780006496434L, "test@t.t"));
        assertEquals(false, state.logout());


        assertEquals(true, state.login("test@t.t", "123456")!=null);
    }
}

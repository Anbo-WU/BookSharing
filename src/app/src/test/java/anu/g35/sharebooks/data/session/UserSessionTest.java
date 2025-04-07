package anu.g35.sharebooks.data.session;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import anu.g35.sharebooks.data.model.Result;
import anu.g35.sharebooks.data.model.User;

import anu.g35.sharebooks.data.model.UserAction;
import anu.g35.sharebooks.data.simulate.Observer;
import anu.g35.sharebooks.data.simulate.Subject;

/**
 * Test of the UserSession class
 *
 * @author u7703248 Chuang Ma
 * @since 2024-05-9
 */
@RunWith(RobolectricTestRunner.class)
public class UserSessionTest{


    private UserSession userSession;
    @Before
    public void setUp() {
        userSession = UserSession.getInstance();
    }



    @Test
    public void testGetInstance() {
        UserSession userSession2 = UserSession.getInstance();
        assert(userSession == userSession2);
    }

    @Test
    public void testLogin() {
        Result<User> result = userSession.login("test", "test");
        assert(result instanceof Result.Error);

        if (!userSession.isLogin()) {
            result = userSession.login("test@t.t", "123456");
            assertEquals("test@t.t", ((Result.Success<User>) result).getData().getId());
            assert (result instanceof Result.Success);
        }

        UserState state = userSession.getUserState();
        assertEquals( true, state instanceof LoginState);

        assertEquals(true, userSession.isLogin());

        assertEquals(true, userSession.logout());
        assertEquals(true, userSession.getUser() == null);

        state = userSession.getUserState();
        assertEquals( true, state instanceof LogoutState);

    }

    @Test
    public void testAction() {
        if (!userSession.isLogin()) {
            Result result = userSession.login("test@t.t", "123456");
            assert (result != null);
            assert (result instanceof Result.Success);
        }

        assertEquals(true, userSession.likeBook(9780006496434L, true));
        assertEquals(true, userSession.likeBook(9780006496434L, false));

        assertEquals(true, userSession.borrowBook(9780060004507L));
        assertEquals(true, userSession.returnBook(9780060004507L));


        assertEquals(true, userSession.followUser("ChuangMa@anu.edu.au", true));
        assertEquals(true, userSession.followUser("ChuangMa@anu.edu.au", false));

    }

    @Test
    public void testObserver() {
        Result result = userSession.login("test@t.t", "123456");
    //    assert (result instanceof Result.Success);

        Observer observer = new Observer() {
            @Override
            public void receiveAction(UserAction action, Subject from) {
                assertEquals(true, action != null);

            }
        };

        userSession.registerObserver(observer);
        userSession.notifyObservers(new UserAction());
        userSession.removeObserver(observer);
    }





}

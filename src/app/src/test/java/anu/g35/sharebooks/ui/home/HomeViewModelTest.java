package anu.g35.sharebooks.ui.home;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import anu.g35.sharebooks.data.model.Result;
import anu.g35.sharebooks.data.model.UserAction;
import anu.g35.sharebooks.data.session.UserSession;
import anu.g35.sharebooks.data.simulate.SimulateSubject;

/**
 * This class is used to test the HomeViewModel class.
 *
 * @author Huizhe_Ruan, u7723366
 * @since 2024-05-9
 */
@RunWith(RobolectricTestRunner.class)
public class HomeViewModelTest {
    private HomeViewModel homeViewModel;
    private SimulateSubject simulateSubject;
    private UserSession userSession;

    @Before
    public void setUp() {
        homeViewModel = new HomeViewModel();
        userSession = UserSession.getInstance();
        simulateSubject = SimulateSubject.getInstance();


    }

    @Test
    public void testGetFunction() {
        assertEquals(true, homeViewModel.getLiveUserActions() != null);
        assertEquals(true, homeViewModel.getUserActions() != null);

    }

    @Test
    public void testReceiveFunction() {
        if (!userSession.isLogin()) {
            Result result = userSession.login("test@t.t", "123456");
            assert (result != null);
            assert (result instanceof Result.Success);
        }

        homeViewModel.receiveMyMessage("aaa");

        UserAction action = new UserAction();
        action.setUserId("test@t.t");
        action.setActionType(UserAction.Type.BORROW);
        action.setAtBookISBN(9780006646006L);

        homeViewModel.receiveAction(action, userSession);
        action.setActionType(UserAction.Type.RETURN);
        homeViewModel.receiveAction(action, userSession);

        action = new UserAction();
        action.setUserId("ChuangMa@anu.edu.au");
        action.setActionType(UserAction.Type.BORROW);
        action.setAtBookISBN(9781933372198L);
        action.setContent("I want to borrow this book");
        action.setAtUserId("test@t.t");

        homeViewModel.receiveAction(action, simulateSubject);

        action.setActionType(UserAction.Type.RETURN);
        homeViewModel.receiveAction(action, simulateSubject);

        action.setActionType(UserAction.Type.SAY);
        homeViewModel.receiveAction(action, simulateSubject);

        action.setActionType(UserAction.Type.LIKE);
        homeViewModel.receiveAction(action, simulateSubject);

        action.setActionType(UserAction.Type.FOLLOW);
        homeViewModel.receiveAction(action, simulateSubject);

        action.setActionType(UserAction.Type.UNFOLLOW);
        homeViewModel.receiveAction(action, simulateSubject);

        action.setActionType(UserAction.Type.DISLIKE);
        homeViewModel.receiveAction(action, simulateSubject);

        assertEquals(true, homeViewModel.getUserActions().size() > 0);
    }





}

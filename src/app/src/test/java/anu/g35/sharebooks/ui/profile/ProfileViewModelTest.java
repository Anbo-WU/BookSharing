package anu.g35.sharebooks.ui.profile;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import anu.g35.sharebooks.data.session.UserSession;


/**
 * This class is used to test the ProfileViewModel class.
 *
 * @author Junfeng Gao, u7615533
 * @since 2024-05-11
 */
@RunWith(RobolectricTestRunner.class)
public class ProfileViewModelTest {
    ProfileViewModel profileViewModel;
    UserSession userSession;

    @Before
    public void setUp() {
        profileViewModel = new ProfileViewModel();
        userSession = UserSession.getInstance();
    }

    @Test
    public void testSetGetFunction() {
        if (!userSession.isLogin()) {
            userSession.login("test@t.t", "123456");
        }
        profileViewModel.setUser("ChuangMa@anu.edu.au");
        profileViewModel.setSession();
        profileViewModel.setUserInfo();
        assertEquals(true, profileViewModel.getID() != null);
        assertEquals(true, profileViewModel.getName() != null);
        assertEquals(true, profileViewModel.getAddress() != null);
        assertEquals(true, profileViewModel.getBiography() != null);
        assertEquals(true, profileViewModel.getAvatar() != null);
        assertEquals(true, profileViewModel.getFansCount() != null);
        assertEquals(true, profileViewModel.getFollowingCount() != null);
        assertEquals(true, profileViewModel.getLikedBooksCount() != null);
        assertEquals(true, profileViewModel.getBorrowBooksCount() != null);
        assertEquals(true, profileViewModel.getIsFollowed() != null);

    }

    @Test
    public void testFollowAndUnFollowFunction() {

        if (!userSession.isLogin()) {
            userSession.login("test@t.t", "123456");
        }

        profileViewModel.setUser("ChuangMa@anu.edu.au");
        profileViewModel.setUserInfo();

        profileViewModel.follow();
        assertEquals(true, profileViewModel.getIsFollowed() != null);

        profileViewModel.unfollow();
        assertEquals(true, profileViewModel.getIsFollowed() != null);

    }

}

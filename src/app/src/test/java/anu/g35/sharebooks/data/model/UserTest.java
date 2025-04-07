package anu.g35.sharebooks.data.model;


import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test of the User class
 *
 * @author u7703248 Chuang Ma
 * @since 2024-05-9
 */
public class UserTest {

    @Test
    public void testNewUser() {
        User user = new User();
        user.setPassword("password");
        user.setName("name");
        user.setId("id");
        user.setBiography("biography");
        user.setAvatar("avatar");
        user.setAddress("address");
        user.setCoordinates("coordinates");
        user.addLikeBook(1234567890L);
        user.addFan("fan");
        user.addFollow("following");

        assertEquals("password", user.getPassword());
        assertEquals("name", user.getName());
        assertEquals("id", user.getId());
        assertEquals("biography", user.getBiography());
        assertEquals("avatar", user.getAvatar());
        assertEquals("address", user.getAddress());
        assertEquals("coordinates", user.getCoordinates());
        assertEquals(true,user.getLikeBooks().contains(1234567890L));
        assertEquals(true,user.getFans().contains("fan"));
        assertEquals(true,user.getFollowing().contains("following"));
        assertEquals(true,user.toString() != null);

        user.removeLikeBook(1234567890L);
        user.removeFan("fan");
        user.removeFollow("following");
    }
}

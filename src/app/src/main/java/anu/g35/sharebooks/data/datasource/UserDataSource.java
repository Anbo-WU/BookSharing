package anu.g35.sharebooks.data.datasource;

import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anu.g35.sharebooks.R;
import anu.g35.sharebooks.ShareBooks;
import anu.g35.sharebooks.data.model.User;

/**
 * This class is a singleton.
 * This class is responsible for loading user data from a JSON file.
 * This class is responsible for authenticating the user.
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-18
 */
public class UserDataSource {

    private static  UserDataSource instance;
    private Map<String, User> users;
    private UserDataSource() {
        try {
            this.users = loadUsersFromJSON();
        } catch (Exception e) {
            Log.e("UserDataSource", "Loading users failed", e);
        }
    }

    /**
     * Returns the instance of the UserDataSource.
     * @return  the instance of the UserDataSource
     */
    public static UserDataSource getInstance() {
        if(instance == null) {
            instance = new UserDataSource();
        }
        return instance;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     *  Authenticates the user.
     * @param userId the user id
     * @param password the password
     * @return  the user
     */
    public User login(String userId, String password) {

        if(userId == null || password == null) {
            return null;
        }
        if(users.containsKey(userId.toLowerCase())) {
            User user = users.get(userId);
            if(user !=null && user.getPassword().equals(password)) {
                return getUser(userId);
            }
        }
        return null;
    }

    /**
     * Returns a new user Object.
     * The password is removed.
     * @param userId the user id
     * @return  the user
     */
    public User getUser(String userId) {
        userId = userId.toLowerCase();
        User user = users.get(userId);
        if(user == null) {
            return null;
        }
        try {
            User newUserObject = (User) user.clone();
            // Remove the password
            newUserObject.setPassword(null);
            return newUserObject;
        } catch (CloneNotSupportedException e) {
            Log.e("UserDataSource", "Cloning user failed", e);
            return null;
        }
    }

    /**
     * Updates the user.
     * The password is not updated.
     * @param user the user
     * @return  true if the user is updated successfully
     */
    public boolean updateUser(User user) {
        if(user == null) {
            return false;
        }
        String userId = user.getId();
        if(users.containsKey(userId)) {
            user.setPassword(users.get(user.getId()).getPassword());
            users.put(userId, user);
            return true;
        }
        return false;
    }

    /**
     * Returns the array of users.
     * @return  the array of users
     */
    private Map<String, User> loadUsersFromJSON() throws Exception {
        JSONObject jsonObject = getJsonObject();
        JSONArray usersArray = jsonObject.getJSONArray("users");
        Map<String, User> userMap = new HashMap<>();

        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject userObject = usersArray.getJSONObject(i);
            User user = new User();
            user.setId(userObject.getString("id"));
            user.setName(userObject.getString("name"));
            user.setPassword(userObject.getString("password"));
            user.setAddress(userObject.getString("address"));
            user.setAvatar(userObject.getString("avatar"));
            user.setBiography(userObject.getString("biography"));
            user.setCoordinates(userObject.getString("coordinates"));

            String fans = userObject.getString("fans");
            List<String> fansList = Arrays.asList(fans.split(","));
            for (String fan : fansList) {
                user.addFan(fan);
            }

            String following = userObject.getString("following");
            List<String> followingList = Arrays.asList(following.split(","));
            for (String follow : followingList) {
                user.addFollow(follow);
            }

            String likeBooks = userObject.getString("like_books");
            List<String> likeBooksList = Arrays.asList(likeBooks.split(","));
            for (String likeBook : likeBooksList) {
                user.addLikeBook(Long.parseLong(likeBook));
            }

            userMap.put(user.getId().toLowerCase(), user);
        }
        return userMap;
    }

    /**
     * Returns the JSON object.
     * @return  the JSON object
     * @throws IOException  if an error occurs
     * @throws JSONException    if an error occurs
     */
    @NonNull
    private static JSONObject getJsonObject() throws IOException, JSONException {
        Resources resources = ShareBooks.getContext().getResources();
        InputStream inputStream = resources.openRawResource(R.raw.users);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        String json = builder.toString();
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject;
    }
}

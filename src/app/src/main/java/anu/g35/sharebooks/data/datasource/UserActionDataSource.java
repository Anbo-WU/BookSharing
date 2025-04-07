package anu.g35.sharebooks.data.datasource;

import android.content.Context;
import android.content.res.AssetManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import anu.g35.sharebooks.ShareBooks;
import anu.g35.sharebooks.data.model.UserAction;

/**
 * This class is responsible for loading user action data from an XML file.
 * This class is responsible for providing the list of user actions.
 *
 * @Author u7703248 Chuang Ma
 * @since 2024-04-28
 */
public class UserActionDataSource {

    private List<UserAction> userActions;

    private static UserActionDataSource instance = null;

    private UserActionDataSource() {
        userActions = loadUserActionFromXML();
    }

    public static UserActionDataSource getInstance() {
        if (instance == null) {
            instance = new UserActionDataSource();
        }
        return instance;
    }

    /**
     * Returns the list of user actions, new list to avoid modification of the original list.
     *
     * @return
     */
    public List<UserAction> getUserActions() {
        List<UserAction> newUserActions = new ArrayList<>();
        for (UserAction userAction : userActions) {
            newUserActions.add(new UserAction(userAction));
        }
        return newUserActions;
    }

    /**
     * reading them from an XML resource file and adding them to the list of user actions.
     *
     * @return a list of UserAction objects
     */
    private List<UserAction> loadUserActionFromXML() {

        // Create a new list to store the parsed UserAction objects
        List<UserAction> userActions = new ArrayList<>();
        Context context = ShareBooks.getContext();

        // Get the XML resource parser for the user_actions XML file
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("data/user_actions.xml");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(inputStream, "UTF-8");

            int eventType = xpp.getEventType();
            UserAction currentUserAction = null;

            // Loop through the XML document
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // Start of a new action, create a new UserAction object
                if (eventType == XmlPullParser.START_TAG) {
                    // Inside an action, set the properties of the UserAction object
                    if (xpp.getName().equals("action")) {
                        currentUserAction = new UserAction();
                    } else if (currentUserAction != null) {
                        if (xpp.getName().equals("action-type")) {
                            switch (xpp.nextText()) {
                                case "SAY":
                                    currentUserAction.setActionType(UserAction.Type.SAY);
                                    break;
                                case "LIKE":
                                    currentUserAction.setActionType(UserAction.Type.LIKE);
                                    break;
                                case "DISLIKE":
                                    currentUserAction.setActionType(UserAction.Type.DISLIKE);
                                    break;
                                case "FOLLOW":
                                    currentUserAction.setActionType(UserAction.Type.FOLLOW);
                                    break;
                                case "UNFOLLOW":
                                    currentUserAction.setActionType(UserAction.Type.UNFOLLOW);
                                    break;
                                case "BORROW":
                                    currentUserAction.setActionType(UserAction.Type.BORROW);
                                    break;
                                case "RETURN":
                                    currentUserAction.setActionType(UserAction.Type.RETURN);
                                    break;
                                default:
                                    continue;
                            }
                        } else if (xpp.getName().equals("user-id")) {
                            currentUserAction.setUserId(xpp.nextText());
                        } else if (xpp.getName().equals("content")) {
                            currentUserAction.setContent(xpp.nextText());
                        } else if (xpp.getName().equals("at-user-id")) {
                            currentUserAction.setAtUserId(xpp.nextText());
                        } else if (xpp.getName().equals("at-book-isbn")) {
                            currentUserAction.setAtBookISBN(Long.parseLong(xpp.nextText()));
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().equals("action")) {
                        // End of an action, add the UserAction object to the list
                        userActions.add(currentUserAction);
                        currentUserAction = null;
                    }
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return userActions;
    }

}

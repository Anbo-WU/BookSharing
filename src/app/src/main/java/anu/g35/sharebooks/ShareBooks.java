package anu.g35.sharebooks;

import android.app.Application;
import android.content.Context;

/**
 * Called when the application is starting.
 * This class is used to get the application context from anywhere in the app.
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-18
 */
public class ShareBooks extends Application {
    private static ShareBooks instance;

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class
     * @throws IllegalStateException if the instance has not been created yet
     */
    public static ShareBooks getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Application instance has not been created yet");
        }
        return instance;
    }


    /**
     * Returns the application context.
     *
     * @return the application context
     */
    public static Context getContext(){
        return getInstance().getApplicationContext();
    }

    /**
     * Called when the application is starting.
     * This method sets the instance of this class (the application).
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // instance = this;
        setInstance(this);
    }

    /**
     * Sets the instance of this class.
     * This method is used for testing purposes.
     * It should not be used in the production code.
     *
     * @param newInstance the new instance of this class
     */
    public static void setInstance(ShareBooks newInstance) {
        instance = newInstance;
    }
}
package hu.ait.android.chau.memorygame;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Chau on 4/15/2015.
 */
public class MemoryGameApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "sp6lUjP6UVq8u5jD6oEZDtKhClvoXN6RkwcXI1kE", "0R8qKHv5uvR5QhKNOFqo22WyB9HUeO9BQ2iug9jn");

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}

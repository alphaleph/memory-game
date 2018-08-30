package hu.ait.android.chau.memorygame;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import hu.ait.android.chau.memorygame.fragment.HighScoresFragment;


public class HighScoresActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        showFragment(HighScoresFragment.TAG);
    }

    private void showFragment(String tag) {
        Fragment fragment = null;
        FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentByTag(tag);

        if (fragment == null) {
            if (HighScoresFragment.TAG.equals(tag)) {
                fragment = new HighScoresFragment();
            }
        }

        if (fragment != null) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.highscores_container, fragment);
            transaction.commit();
        }

    }

}

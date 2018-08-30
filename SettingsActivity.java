package hu.ait.android.chau.memorygame;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import hu.ait.android.chau.memorygame.fragment.SettingsFragment;


public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        showFragment(SettingsFragment.TAG);
    }

    private void showFragment(String tag) {
        Fragment fragment = null;
        FragmentManager fManager = getSupportFragmentManager();
        fragment = fManager.findFragmentByTag(tag);
        if (fragment == null) {
            if (SettingsFragment.TAG.equals(tag)) {
                fragment = new SettingsFragment();
            }
        }

        if (fragment != null) {
            FragmentTransaction transaction = fManager.beginTransaction();
            transaction.replace(R.id.settings_container, fragment, tag);
            //transaction.addToBackStack(null);
            transaction.commit();
        }

    }

}

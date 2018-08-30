package hu.ait.android.chau.memorygame;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import hu.ait.android.chau.memorygame.fragment.DifficultyDialogFragment;
import hu.ait.android.chau.memorygame.fragment.MainMenuFragment;


public class MainMenuActivity
        extends ActionBarActivity
        implements  DifficultyDialogFragment.OptionsFragmentInterface,
                    MainMenuFragment.MainMenuInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        showFragment(MainMenuFragment.TAG);
    }

    private void showFragment(String tag) {
        Fragment fragment = null;
        FragmentManager fManager = getSupportFragmentManager();
        fragment = fManager.findFragmentByTag(tag);
        if (fragment == null) {
            if (MainMenuFragment.TAG.equals(tag)) {
                fragment = new MainMenuFragment();
            }
        }

        if (fragment != null) {
            FragmentTransaction transaction = fManager.beginTransaction();
            transaction.replace(R.id.mainmenuContainer, fragment, tag);
            //transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    public void showDifficultySelector() {
        DifficultyDialogFragment diffFrag = new DifficultyDialogFragment();
        diffFrag.show(getSupportFragmentManager(), DifficultyDialogFragment.TAG);
    }

    @Override
    public void onOptionsFragmentResult(String difficulty) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(getString(R.string.key_difficulty), difficulty);
        startActivity(intent);
    }
}

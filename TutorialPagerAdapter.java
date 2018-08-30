package hu.ait.android.chau.memorygame.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import hu.ait.android.chau.memorygame.fragment.TutorialEnjoyFragment;
import hu.ait.android.chau.memorygame.fragment.TutorialGameplayFragment;
import hu.ait.android.chau.memorygame.fragment.TutorialResetFragment;
import hu.ait.android.chau.memorygame.fragment.TutorialSetUpFragment;
import hu.ait.android.chau.memorygame.fragment.TutorialStartFragment;
import hu.ait.android.chau.memorygame.fragment.TutorialWinnersFragment;

/**
 * Created by Chau on 4/10/2015.
 */
public class TutorialPagerAdapter extends FragmentPagerAdapter {

    public static final int NUM_PAGES = 6;
    public static final String SETUP_TITLE = "Getting Started";
    public static final String START_GAME_TITLE = "Finding the Start Button";
    public static final String GAMEPLAY_TITLE = "Rules of The Game";
    public static final String RESET_TITLE = "The Power of Reset";
    public static final String WINNERS_TITLE = "To The Victors Go The Spoils";
    public static final String ENJOY_TITLE = "Off To Your Adventure!";

    public TutorialPagerAdapter(FragmentManager fm) { super(fm); }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new TutorialSetUpFragment();
            case 1:
                return new TutorialStartFragment();
            case 2:
                return new TutorialGameplayFragment();
            case 3:
                return new TutorialResetFragment();
            case 4:
                return new TutorialWinnersFragment();
            case 5:
                return new TutorialEnjoyFragment();
            default:
                return new TutorialSetUpFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return SETUP_TITLE;
            case 1:
                return START_GAME_TITLE;
            case 2:
                return GAMEPLAY_TITLE;
            case 3:
                return RESET_TITLE;
            case 4:
                return WINNERS_TITLE;
            case 5:
                return ENJOY_TITLE;
            default:
                return SETUP_TITLE;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}

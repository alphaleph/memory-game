package hu.ait.android.chau.memorygame;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import hu.ait.android.chau.memorygame.fragment.GameFragment;
import hu.ait.android.chau.memorygame.fragment.WinnersDialogFragment;


public class GameActivity extends ActionBarActivity
        implements WinnersDialogFragment.WinnersFragmentInterface,
        GameFragment.GameInterface {

    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        difficulty = getIntent().getStringExtra(getString(R.string.key_difficulty));
        if (!"".equals(difficulty)) {
            showGameFragment(difficulty);
        }
    }

    private void showGameFragment(String difficulty) {
        Fragment fragment = null;
        FragmentManager fManager = getSupportFragmentManager();
        fragment = fManager.findFragmentByTag(GameFragment.TAG);
        if (fragment == null) {
            fragment = new GameFragment();
        }

        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.key_difficulty), difficulty);
            fragment.setArguments(bundle);

            FragmentTransaction transaction = fManager.beginTransaction();
            transaction.replace(R.id.game_container, fragment, GameFragment.TAG);
            transaction.commit();
        }
    }

    @Override
    public void onWinnersFragmentResult(String option) {
        switch (option) {
            case WinnersDialogFragment.RESTART_GAME:
                GameFragment.resetGame();
                break;
            case WinnersDialogFragment.HIGH_SCORES:
                // TODO: Perform Internet Check.
                startActivity(new Intent(this, HighScoresActivity.class));
                finish();
                break;
            case WinnersDialogFragment.MAIN_MENU:
                finish();
                break;
        }
    }

    @Override
    public void showWinnersDialog(long gameTime) {
        Bundle bundle = new Bundle();
        bundle.putLong(GameFragment.ELAPSED_TIME_KEY, gameTime);

        WinnersDialogFragment winFrag = new WinnersDialogFragment();
        winFrag.setArguments(bundle);
        winFrag.show(getSupportFragmentManager(), WinnersDialogFragment.TAG);
    }
}

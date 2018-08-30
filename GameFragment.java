package hu.ait.android.chau.memorygame.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import hu.ait.android.chau.memorygame.R;
import hu.ait.android.chau.memorygame.adapter.BoardGridAdaptor;
import hu.ait.android.chau.memorygame.model.MemoryGameModel;

/**
 * Created by Chau on 4/10/2015.
 */
public class GameFragment extends Fragment {

    public static final String TAG = "GameFragment";
    public static final String ELAPSED_TIME_KEY = "ELAPSED_TIME_KEY";
    public static final String SCORES_TABLE_NAME = "Scores";
    public static final String TABLE_USER_NAME = "UserName";
    public static final String TABLE_DATE = "Date";
    public static final String TABLE_SCORE = "Score";
    public static final String NO_USERNAME = "You need a username to save your score!";
    public static final String NO_INTERNET_ALERT = "Internet connection needed to save score.";
    public static final int MISMATCH_DELAY = 1000;
    public static String difficulty;

    private TextView tvDifficulty, tvTime;
    private static GridView gridBoard;
    private Button btnReset;
    private int turnGuessCount, numMatchings = 0;
    private HashMap<Integer, Integer> prevGuesses = new HashMap<Integer, Integer>();
    private static long prevTime, elapsedTime = 0;
    private Timer timer;
    private String timeString;
    private static BoardGridAdaptor adapter;

    private GameInterface gameInterface;

    public interface GameInterface {
        public void showWinnersDialog(long gameTime);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            gameInterface= (GameInterface) this.getActivity();
        }
        catch (ClassCastException e) {
            throw new ClassCastException(this.getActivity().toString() +
                    " must implement GameInterface.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, null);

        difficulty = setGameDifficulty();

        tvDifficulty = (TextView) rootView.findViewById(R.id.tv_difficulty);
        tvDifficulty.setText(getString(R.string.tvDifficulty) + " " + difficulty);

        tvTime = (TextView) rootView.findViewById(R.id.tvTime);

        btnReset = (Button) rootView.findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        gridBoard = (GridView) rootView.findViewById(R.id.gridBoard);
        adapter = new BoardGridAdaptor(getActivity());
        gridBoard.setAdapter(adapter);
        gridBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!MemoryGameModel.getInstance().isTileRevealed(position)) {
                    revealTile(position);
                    Animation waitAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_wait);
                    view.startAnimation(waitAnim);

                    if (foundMatching(position)) {
                        checkWinner();
                    }

                }
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        prevTime = System.currentTimeMillis();
        timer = new Timer();
        timer.schedule(new RunTimeTimerTask(), 0, 1);
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
    }

    private class RunTimeTimerTask extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    elapsedTime += (System.currentTimeMillis() - prevTime);
                    timeString = String.format("%02d:%02d:%02d",
                            (elapsedTime / 1000 / 60 / 60) % 60,
                            (elapsedTime / 1000 / 60) % 60,
                            (elapsedTime / 1000) % 60);
                    tvTime.setText(getString(R.string.tv_time) + " " + timeString);
                    prevTime = System.currentTimeMillis();
                }
            });
        }
    }

    private boolean foundMatching(int position) {
        int guessValue = MemoryGameModel.getInstance().getTile(position).getValue();
        if (turnGuessCount == MemoryGameModel.MATCHING_SIZE) {
            for (int prevGuess : prevGuesses.values()) {
                if (guessValue != prevGuess) {
                    prevGuesses.put(position, guessValue);
                    resetTurnMisMatch();
                    return false;
                }
            }
            resetTurnMatch();
            return true;

        } else {
            prevGuesses.put(position, guessValue);
            return false;
        }
    }

    private void revealTile(int position) {
        MemoryGameModel.getInstance().setTileRevealed(position, true);
        adapter.notifyDataSetChanged();
        gridBoard.invalidateViews();
        turnGuessCount++;
    }

    private void resetTurnMatch() {
        prevGuesses.clear();
        turnGuessCount = 0;
        numMatchings++;
    }

    private void resetTurnMisMatch() {
        for (int position : prevGuesses.keySet()) {
            MemoryGameModel.getInstance().setTileRevealed(position, false);
        }
        adapter.notifyDataSetChanged();
        prevGuesses.clear();
        turnGuessCount = 0;
    }

    private void checkWinner() {
        if (numMatchings ==
                (MemoryGameModel.getInstance().getSize() / MemoryGameModel.MATCHING_SIZE)) {
            numMatchings = 0;
            if (isNetworkAvailable()) {
                saveScore();
            } else {
                Toast.makeText(getActivity(), NO_INTERNET_ALERT, Toast.LENGTH_SHORT).show();
            }
            gameInterface.showWinnersDialog(elapsedTime);
        }
    }

    private void saveScore() {
        SharedPreferences sp = getActivity().
                getSharedPreferences(SettingsFragment.PREF_SETTINGS, Context.MODE_PRIVATE);
        String userName = sp.getString(SettingsFragment.KEY_NAME, "");
        Date currDate = new Date();
        if (!"".equals(userName)) {
            ParseObject po = new ParseObject(SCORES_TABLE_NAME);
            po.put(TABLE_USER_NAME, userName);
            po.put(TABLE_DATE, currDate);
            po.put(TABLE_SCORE, elapsedTime);
            po.saveInBackground();
        }
        else {
            Toast.makeText(getActivity(), NO_USERNAME, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private String setGameDifficulty() {
        String keyDifficulty = getString(R.string.key_difficulty);
        String difficulty = getArguments().getString(keyDifficulty);
        MemoryGameModel.setDifficulty(difficulty);
        return difficulty;
    }

    public static void resetGame() {
        elapsedTime = 0;
        prevTime = System.currentTimeMillis();
        MemoryGameModel.getInstance().resetGameModel();
        adapter.notifyDataSetChanged();
        gridBoard.invalidateViews();
    }
}

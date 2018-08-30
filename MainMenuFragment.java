package hu.ait.android.chau.memorygame.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import hu.ait.android.chau.memorygame.GameActivity;
import hu.ait.android.chau.memorygame.HighScoresActivity;
import hu.ait.android.chau.memorygame.R;
import hu.ait.android.chau.memorygame.SettingsActivity;
import hu.ait.android.chau.memorygame.TutorialActivity;

/**
 * Created by Chau on 4/10/2015.
 */
public class MainMenuFragment extends Fragment {

    public static final String TAG = "MainMenuFragment";
    public static final String NO_INTERNET_ALERT = "Internet connection needed for High Scores.";

    public interface MainMenuInterface {
        public void showDifficultySelector();
    }

    private MainMenuInterface mainMenuInterface;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mainMenuInterface = (MainMenuInterface) this.getActivity();
        }
        catch (ClassCastException e) {
            throw new ClassCastException(this.getActivity().toString() +
                    " must implement MainMenuInterface.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mainmenu, null);

        Button btnNewGame = (Button) rootView.findViewById(R.id.btnNewGame);
        Button btnSettings = (Button) rootView.findViewById(R.id.btnSettings);
        Button btnTutorial = (Button) rootView.findViewById(R.id.btnTutorial);
        Button btnHighScores = (Button) rootView.findViewById(R.id.btnHighScores);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMenuInterface.showDifficultySelector();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuFragment.this.getActivity(), SettingsActivity.class));
            }
        });

        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuFragment.this.getActivity(), TutorialActivity.class));
            }
        });

        btnHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    startActivity(new Intent(MainMenuFragment.this.getActivity(), HighScoresActivity.class));
                } else {
                    Toast.makeText(getActivity(), NO_INTERNET_ALERT, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

package hu.ait.android.chau.memorygame.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Chau on 4/15/2015.
 */
public class WinnersDialogFragment extends DialogFragment
        implements DialogInterface.OnClickListener {

    public static final String TAG = "WinnersDialogFragment";
    public static final String RESTART_GAME = "Restart Game";
    public static final String HIGH_SCORES = "High Scores";
    public static final String MAIN_MENU = "Main Menu";
    public static final String TITLE = "Congrats, you won in ";

    public interface WinnersFragmentInterface {
        public void onWinnersFragmentResult(String option);
    }

    private String[] options = {RESTART_GAME, HIGH_SCORES, MAIN_MENU};
    private WinnersFragmentInterface winnersFragmentInterface;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Long elapsedTime = getArguments().getLong(GameFragment.ELAPSED_TIME_KEY);
        String timeString = String.format("%02d:%02d:%02d",
                (elapsedTime / 1000 / 60 / 60) % 60,
                (elapsedTime / 1000 / 60) % 60,
                (elapsedTime / 1000) % 60);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(TITLE + timeString + "!");
        builder.setItems(options, this);
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            winnersFragmentInterface = (WinnersFragmentInterface) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement WinnersFragmentInterface.");
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        winnersFragmentInterface.onWinnersFragmentResult(options[which]);
    }
}

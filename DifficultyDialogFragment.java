package hu.ait.android.chau.memorygame.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.ait.android.chau.memorygame.R;

/**
 * Created by Chau on 4/10/2015.
 */
public class DifficultyDialogFragment extends DialogFragment
        implements DialogInterface.OnClickListener {

    public static final String TAG = "DifficultyDialogFragment";
    public static final String DIALOG_TITLE = "Choose Difficulty Level";
    public static final String DIFFICULTY_EASY = "Easy";
    public static final String DIFFICULTY_MEDIUM = "Medium";
    public static final String DIFFICULTY_HARD = "Hard";

    public interface OptionsFragmentInterface {
        public void onOptionsFragmentResult(String difficulty);
    }

    private String[] options = {DIFFICULTY_EASY, DIFFICULTY_MEDIUM, DIFFICULTY_HARD};
    private OptionsFragmentInterface optionsFragmentInterface;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(DIALOG_TITLE);
        builder.setItems(options, this);
        builder.setCancelable(true);
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            optionsFragmentInterface = (OptionsFragmentInterface) activity;
            }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement OptionsFragmentInterface.");
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        optionsFragmentInterface.onOptionsFragmentResult(options[which]);
    }
}

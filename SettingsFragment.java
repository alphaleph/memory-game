package hu.ait.android.chau.memorygame.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import hu.ait.android.chau.memorygame.R;

/**
 * Created by Chau on 4/10/2015.
 */
public class SettingsFragment extends Fragment {

    public static final String TAG = "SettingsFragment";
    public static final String PREF_SETTINGS = "MySettings";
    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_AGE = "KEY_AGE";
    public static final String KEY_GENDER = "KEY_GENDER";

    private EditText etName;
    private EditText etAge;
    private RadioGroup rgGender;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_settings, null);

        etName = (EditText) rootView.findViewById(R.id.etName);
        etAge = (EditText) rootView.findViewById(R.id.etAge);
        rgGender = (RadioGroup) rootView.findViewById(R.id.rgGender);
        Button btnDone = (Button) rootView.findViewById(R.id.btnDone);

        fillSettings();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings(rootView);
                Toast.makeText(getActivity(), "Settings saved.", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void saveSettings(View rootView) {
        int selectedRadId = rgGender.getCheckedRadioButtonId();

        SharedPreferences sp = getActivity().getSharedPreferences(
                PREF_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_NAME, etName.getText().toString());
        editor.putString(KEY_AGE, etAge.getText().toString());
        editor.putString(KEY_GENDER, ((RadioButton) rootView.findViewById(selectedRadId))
                .getText().toString());
        editor.commit();
    }

    private void fillSettings() {
        SharedPreferences sp = getActivity().getSharedPreferences(PREF_SETTINGS, Context.MODE_PRIVATE);

        String name = sp.getString(KEY_NAME, "");
        if (!"".equals(name)) {
            etName.setText(name);
        }

        String age = sp.getString(KEY_AGE, "");
        if (!"".equals(age)) {
            etAge.setText(age);
        }

        String gender = sp.getString(KEY_GENDER, "");
        if (!"".equals(gender)) {
            if (getString(R.string.rad_male).equals(gender)) {
                rgGender.check(R.id.radMale);
            }
            else if (getString(R.string.rad_female).equals(gender)) {
                rgGender.check(R.id.radFemale);
            }
        }
    }

}

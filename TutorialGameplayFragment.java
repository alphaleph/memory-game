package hu.ait.android.chau.memorygame.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.ait.android.chau.memorygame.R;

/**
 * Created by Chau on 4/16/2015.
 */
public class TutorialGameplayFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tutorial_gameplay, container, false);
        return rootView;
    }
}

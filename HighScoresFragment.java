package hu.ait.android.chau.memorygame.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import hu.ait.android.chau.memorygame.R;

/**
 * Created by Chau on 4/10/2015.
 */
public class HighScoresFragment extends Fragment {

    public static final String TAG = "HighScoresFragment";
    public static final int QUERY_LIMIT = 10;

    TextView tvScores;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_high_scores, null);

        tvScores = (TextView) rootView.findViewById(R.id.tvScores);
        setHighScores();

        return rootView;
    }

    private void setHighScores() {
        ParseQuery<ParseObject> pq = new ParseQuery<ParseObject>(GameFragment.SCORES_TABLE_NAME);
        pq.setLimit(QUERY_LIMIT);
        pq.orderByAscending(GameFragment.TABLE_SCORE);
        pq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                StringBuilder sb = new StringBuilder();
                for (ParseObject po : parseObjects) {
                    long score = po.getLong(GameFragment.TABLE_SCORE);
                    String timeString = String.format("%02d:%02d:%02d",
                            (score / 1000 / 60 / 60) % 60,
                            (score / 1000 / 60) % 60,
                            (score / 1000) % 60);
                    sb.append(po.getString(GameFragment.TABLE_USER_NAME)+" ---> "+
                            timeString+"\n");
                }

                tvScores.setText(sb.toString());
            }
        });
    }
}
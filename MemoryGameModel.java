package hu.ait.android.chau.memorygame.model;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import hu.ait.android.chau.memorygame.R;
import hu.ait.android.chau.memorygame.fragment.DifficultyDialogFragment;

/**
 * Created by Chau on 4/12/2015.
 */
public class MemoryGameModel {

    public static final int SIZE_EASY = 12;
    public static final int SIZE_MED = 16;
    public static final int SIZE_HARD = 20;
    public static final int MATCHING_SIZE = 2;

    private static MemoryGameModel instance = null;

    public static MemoryGameModel getInstance() {
        if (instance == null || changedDifficulty) {
            instance = new MemoryGameModel();
            changedDifficulty = false;
        }
        return instance;
    }

    private static String difficulty = "";
    private static boolean changedDifficulty = false;

    public static void setDifficulty(String setDiff) {
        difficulty = setDiff;
        changedDifficulty = true;
    }

    private List<TileType> model = new ArrayList<TileType>();
    private boolean[] viewModel;

    private MemoryGameModel() {
        int size;
        switch (difficulty) {
            case DifficultyDialogFragment.DIFFICULTY_EASY:
                size = SIZE_EASY;
                break;
            case DifficultyDialogFragment.DIFFICULTY_MEDIUM:
                size = SIZE_MED;
                break;
            case DifficultyDialogFragment.DIFFICULTY_HARD:
                size = SIZE_HARD;
                break;
            default:
                size = SIZE_EASY;
        }
        createModel(size);
        createViewModel(size);
    }

    private void createModel(int size) {
        for (int i = 0; i < size / MATCHING_SIZE; i++) {
            for (int j = 0; j < MATCHING_SIZE; j++) {
                model.add(TileType.fromInt(i));
            }
        }
        shuffleTiles();
    }

    private void createViewModel(int size) {
        viewModel = new boolean[size];
        Arrays.fill(viewModel, false);
    }

    public void resetGameModel() {
        Arrays.fill(viewModel, false);
        shuffleTiles();
    }

    private void shuffleTiles() {
        Collections.shuffle(model, new Random(System.currentTimeMillis()));
    }

    public int getSize() {
        if (model != null) {
            return model.size();
        } else {
            throw new NullPointerException("getSize: Model is not instantiated.");
        }
    }

    public boolean isTileRevealed(int pos) {
        return viewModel[pos];
    }

    public void setTileRevealed(int pos, boolean revealState) {
        viewModel[pos] = revealState;
    }

    public TileType getTile(int pos) {
        if (model != null) {
            return model.get(pos);
        } else {
            throw new NullPointerException("getTile: Model is not instantiated.");
        }
    }

    /* ################################################################################## */

    public static enum TileType {
        TileType0(0, R.drawable.sample_0),
        TileType1(1, R.drawable.sample_1),
        TileType2(2, R.drawable.sample_2),
        TileType3(3, R.drawable.sample_3),
        TileType4(4, R.drawable.sample_4),
        TileType5(5, R.drawable.sample_5),
        TileType6(6, R.drawable.sample_6),
        TileType7(7, R.drawable.sample_7),
        TileType8(8, R.drawable.sample_8),
        TileType9(9, R.drawable.sample_9);

        private int value, iconId;

        TileType(int value, int iconId) {
            this.value = value;
            this.iconId = iconId;
        }

        public int getValue() {
            return value;
        }

        public int getIconId() {
            return iconId;
        }

        public static TileType fromInt(int value) {
            switch (value) {
                case 0:
                    return TileType.TileType0;
                case 1:
                    return TileType.TileType1;
                case 2:
                    return TileType.TileType2;
                case 3:
                    return TileType.TileType3;
                case 4:
                    return TileType.TileType4;
                case 5:
                    return TileType.TileType5;
                case 6:
                    return TileType.TileType6;
                case 7:
                    return TileType.TileType7;
                case 8:
                    return TileType.TileType8;
                case 9:
                    return TileType.TileType9;
            }
            return TileType.TileType0;
        }
    }

}
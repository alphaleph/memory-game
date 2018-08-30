package hu.ait.android.chau.memorygame.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import hu.ait.android.chau.memorygame.R;
import hu.ait.android.chau.memorygame.model.MemoryGameModel;

/**
 * Created by Chau on 4/12/2015.
 */
public class BoardGridAdaptor extends BaseAdapter {
    public static final int IMAGE_SIZE = 85;
    public static final int IMAGE_PADDING = 8;
    Context context;

    public BoardGridAdaptor(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return MemoryGameModel.getInstance().getSize();
    }

    @Override
    public MemoryGameModel.TileType getItem(int position) {
        return MemoryGameModel.getInstance().getTile(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv = new ImageView(context);
        if (convertView == null) {
            iv.setLayoutParams(new GridView.LayoutParams(IMAGE_SIZE, IMAGE_SIZE));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setPadding(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING);
        } else {
            iv = (ImageView) convertView;
        }

        if (MemoryGameModel.getInstance().isTileRevealed(position)) {
            iv.setImageResource(MemoryGameModel.getInstance().getTile(position).getIconId());
        } else {
            iv.setImageResource(R.drawable.unknown_tile);
        }
        return iv;
    }

}

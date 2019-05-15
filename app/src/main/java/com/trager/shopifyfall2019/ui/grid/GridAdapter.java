package com.trager.shopifyfall2019.ui.grid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<TextView> tiles;

    public GridAdapter(Context mContext, List<TextView> tiles) {
        this.mContext = mContext;
        this.tiles = tiles;
    }

    @Override
    public int getCount() {
        return tiles.size();
    }

    @Override
    public Object getItem(int position) {
        return tiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void remove(int position) {
        tiles.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return tiles.get(position);
    }
}

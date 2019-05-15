package com.trager.shopifyfall2019.ui.grid;

import android.content.Context;
import android.graphics.Color;

import com.trager.shopifyfall2019.model.Tile;

public class TileView extends android.support.v7.widget.AppCompatTextView {

    private Tile model;

    public TileView(Context context, Tile model) {
        super(context);
        this.model = model;
        setText(model.getLetter());

    }

    public int getRow() {
        return model.getRow();
    }

    public void setRow(int row) {
        model.setRow(row);
    }

    public int getCol() {
        return model.getCol();
    }

    public void setCol(int col) {
        model.setCol(col);
    }

    public boolean isValidSelection() {
        return model.isSelectable();
    }

    public void setValidSelection(boolean validSelection) {
        model.setSelectable(validSelection);
    }

    public void setEnabled(boolean enabled) {
        if (!enabled) {
            this.setBackgroundColor(Color.parseColor("#BDBDBD"));
            setValidSelection(false);
        } else {
            this.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        model.setEnabled(enabled);
    }

    public boolean isEnabled() {
        return model.isEnabled();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the   key that will make the height equivalent to its width
    }
}

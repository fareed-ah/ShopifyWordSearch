package com.trager.shopifyfall2019.model;

public class Tile {
    private String letter;
    private int row;
    private int col;
    private boolean isSelectable;
    private boolean enabled;

    public Tile(String letter, int row, int col, boolean isSelectable, boolean enabled) {
        this.letter = letter;
        this.row = row;
        this.col = col;
        this.isSelectable = isSelectable;
        this.enabled = enabled;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public void setSelectable(boolean selectable) {
        if (isEnabled()) {
            isSelectable = selectable;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

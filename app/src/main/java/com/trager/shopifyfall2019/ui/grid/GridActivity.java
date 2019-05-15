package com.trager.shopifyfall2019.ui.grid;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.trager.shopifyfall2019.R;
import com.trager.shopifyfall2019.model.Grid;
import com.trager.shopifyfall2019.model.Tile;
import com.trager.shopifyfall2019.model.WordBank;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GridActivity extends AppCompatActivity implements View.OnTouchListener {

    private Grid grid;
    private GridView gridView;
    private GridView wordListGridView;
    private String word;
    private Stack<TileView> tileViewSelected;
    private String currentDirection;
    public static final String GRID_SIZE_EXTRA = "GRID_SIZE";
    public static final String WORD_COUNT_EXTRA = "WORD_COUNT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        setTitle("Word Search");
        WordBank words = new WordBank(this);
        word = "";
        int gridSize = getIntent().getIntExtra(GRID_SIZE_EXTRA, 10);
        int amountOfWords = getIntent().getIntExtra(WORD_COUNT_EXTRA, 6);
        grid = new Grid(gridSize, gridSize, words.getWords(amountOfWords, gridSize));

        gridView = findViewById(R.id.wordSearchGridView);
        gridView.setNumColumns(gridSize);
        GridAdapter gridAdapter = new GridAdapter(this, getWordSearchTiles(grid.getTiles()));
        gridView.setAdapter(gridAdapter);
        gridView.setOnTouchListener(this);

        wordListGridView = findViewById(R.id.wordsListGridView);
        wordListGridView.setAdapter(new GridAdapter(this, getWordTiles(grid.getWords())));

        tileViewSelected = new Stack<>();

    }

    private List<TextView> getWordSearchTiles(Tile[] tiles) {
        List<TextView> views = new ArrayList<>();
        for (Tile letter : tiles) {
            TileView tileView = new TileView(this, letter);
            tileView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tileView.setGravity(Gravity.FILL);
            // Set the TextView text font family and text size
            tileView.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
            tileView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            tileView.setTextColor(Color.parseColor("#000000"));
            tileView.setBackgroundColor(Color.parseColor("#ffffff"));
            //tileView.setOnTouchListener(this);
            views.add(tileView);
        }
        return views;
    }

    private List<TextView> getWordTiles(List<String> words) {
        List<TextView> views = new ArrayList<>();
        for (String word : words) {
            TextView wordView = new TextView(this);
            wordView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            wordView.setGravity(Gravity.FILL);
            // Set the TextView text font family and text size
            wordView.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
            wordView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            wordView.setTextColor(Color.parseColor("#ffffff"));
            wordView.setBackgroundColor(Color.parseColor("#29b6f6"));
            wordView.setText(word);
            views.add(wordView);
        }
        return views;
    }

    public boolean containsCaseInsensitive(String s, List<String> l) {
        for (String string : l) {
            if (string.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCompleteWord(String word) {
        if (containsCaseInsensitive(word, grid.getWords())) {
            int size = tileViewSelected.size();
            for (int i = 0; i < size; i++) {
                tileViewSelected.pop().setEnabled(false);
            }

            for (int i = 0; i < wordListGridView.getAdapter().getCount(); i++) {
                TextView wordView = (TextView) wordListGridView.getAdapter().getItem(i);
                if (wordView.getText().toString().equalsIgnoreCase(word)) {
                    wordView.setPaintFlags(wordView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    ((GridAdapter) wordListGridView.getAdapter()).remove(i);
                }
            }
            this.word = "";
            grid.resetSelectableTiles();
            return true;
        }
        return false;
    }

    private String getDirection(int prevRow, int prevCol, int currRow, int currCol) {
        if (currRow == prevRow - 1) {
            if (currCol == prevCol) {
                return "UP";
            } else if (currCol == prevCol + 1) {
                return "TOP_RIGHT";
            } else if (currCol == prevCol - 1) {
                return "TOP_LEFT";
            }
        } else if (currRow == prevRow + 1) {
            if (currCol == prevCol) {
                return "DOWN";
            } else if (currCol == prevCol + 1) {
                return "BOTTOM_RIGHT";
            } else if (currCol == prevCol - 1) {
                return "BOTTOM_LEFT";
            }
        } else if (currRow == prevRow) {
            if (currCol == prevCol + 1) {
                return "RIGHT";
            } else if (currCol == prevCol - 1) {
                return "LEFT";
            }
        }
        return "NONE";
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        TextView location = findViewById(R.id.location);
        location.setText(x + ", " + y);
        TileView view = null;
        for (int i = 0; i < gridView.getAdapter().getCount(); i++) {
            TileView selectedView = (TileView) gridView.getAdapter().getItem(i);
            if (selectedView.getX() <= x && (selectedView.getX() + selectedView.getWidth()) >= x && selectedView.getY() <= y && selectedView.getY() + selectedView.getHeight() >= y) {
                view = selectedView;
            }
        }

        if (view == null || !view.isEnabled()) {
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {//Start tracking letters
            selectTile(view);
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            selectTile(view);
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            while (!tileViewSelected.isEmpty()) {
                tileViewSelected.pop().setBackgroundColor(Color.parseColor("#FFFFFF"));
                word = "";
                currentDirection = "NONE";
                grid.resetSelectableTiles();
            }
        }
        return false;
    }

    private void selectTile(TileView view) {
        if (view.isValidSelection()) {
            word += view.getText().toString();
            view.setValidSelection(false);
            view.setBackgroundColor(Color.parseColor("#FFFF00"));
            if (tileViewSelected.empty()) {
                tileViewSelected.push(view);
                grid.setSelectableTiles(view.getRow(), view.getCol(), "NONE");
            } else {
                int prevRow = tileViewSelected.peek().getRow();
                int prevCol = tileViewSelected.peek().getCol();
                tileViewSelected.push(view);
                currentDirection = getDirection(prevRow, prevCol, view.getRow(), view.getCol());
                grid.setSelectableTiles(view.getRow(), view.getCol(), currentDirection);
            }
            isCompleteWord(word);
        }
    }
}

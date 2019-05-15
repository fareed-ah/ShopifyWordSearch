package com.trager.shopifyfall2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.trager.shopifyfall2019.ui.grid.GridActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Word Search");
        setContentView(R.layout.activity_main);
    }

    public void onEasyClicked(View view) {
        startActivity(10, 6);
    }

    public void onMediumClicked(View view) {
        startActivity(15, 9);
    }

    public void onHardClicked(View view) {
        startActivity(20, 12);
    }

    private void startActivity(int gridSize, int words) {
        Intent intent = new Intent(this, GridActivity.class);
        intent.putExtra(GridActivity.GRID_SIZE_EXTRA, gridSize);
        intent.putExtra(GridActivity.WORD_COUNT_EXTRA, words);
        startActivity(intent);
    }
}

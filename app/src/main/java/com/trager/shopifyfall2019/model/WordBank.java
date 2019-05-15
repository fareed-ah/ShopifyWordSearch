package com.trager.shopifyfall2019.model;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordBank {

    private Context mContext;
    private static List<String> wordsList;

    public WordBank(Context context) {
        this.mContext = context;
        if (wordsList == null || wordsList.isEmpty()) {
            wordsList = readLine("words.txt");
        }
    }

    public List<String> getWords(int amount, int size) {
        List<String> words = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < amount; i++) {
            String word = wordsList.get(rand.nextInt(wordsList.size()));
            if (word.length() <= size) {
                words.add(word);
                wordsList.remove(word);
            } else {
                i--;
            }
        }
        return words;
    }

    private List<String> readLine(String path) {
        List<String> mLines = new ArrayList<>();

        AssetManager am = mContext.getAssets();
        InputStream is = null;
        try {
            is = am.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null)
                mLines.add(line);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return mLines;
    }
}
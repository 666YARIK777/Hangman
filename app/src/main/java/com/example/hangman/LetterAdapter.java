package com.example.hangman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class LetterAdapter extends BaseAdapter {
    char[] lrs = new char[32];
    private LayoutInflater letterInfLatter;

    public LetterAdapter(Context c) {
        int i = 0;
        for (char ch = 'А'; ch <= 'Я'; ch++) {
            if (ch == 'Ё') {
                continue;
            }
            lrs[i++] = (char) ch;
        }

        letterInfLatter = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return 32;
    }

    @Override
    public Object getItem(int position) {
        return "" + lrs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Button letterBtn;
        if (view == null) {
            letterBtn = (Button) letterInfLatter.inflate(R.layout.letter, viewGroup,
                    false);
        } else {
            letterBtn = (Button) view;
        }
        letterBtn.setText("" + lrs[position]);
        return letterBtn;
    }
}

package com.example.english.mine;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentController;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.Random;

public class Game{
    int[][] minefield;
    Button[][] buttonfield;
    GridLayout grid_l;
    int i;
    int j;

    public void setGame(GridLayout grid_l, Button[][] button){
        this.grid_l = grid_l;
        buttonfield = button;
        minefield = new int[10][10];
        for(int u = 0; u<10; u++)
            for(int y = 0; y<10;y++)
                minefield[u][y]=0;
        int mine = 0;
        while(mine !=20){
            Random u = new Random();
            int x = u.nextInt(10);
            int y = u.nextInt(10);
            if(minefield[x][y]==0){
                minefield[x][y] = 1;
                mine++;
            }
        }
        buttonfield = new Button[10][10];
        for( i = 0; i<10;i++) {
            for ( j = 0; j < 10; i++) {
                setDefaults(buttonfield[i][j]);
                buttonfield[i][j].setOnClickListener(new View.OnClickListener() {
                    int x = i;
                    int y = j;
                    @Override
                    public void onClick(View v) {
                        click(buttonfield[x][y], x, y);
                    }
                });
            }
        }
    }

    public void setDefaults(Button b){
        b.setBackgroundColor(Color.BLACK);
        b.setEnabled(true);
    }

    public void click(Button b, int i, int j){
        if(minefield[i][j]==1)
            stopGame("lost");
        else{
            b.setBackgroundColor(Color.WHITE);
            int mines_around = 0;
            for(int u = -1; u<2; u++) {
                for (int y = -1; y < 2; y++) {
                    try {
                        mines_around += minefield[i + u][y + j];
                    } finally {

                    }
                }
            }
            b.setText(mines_around);
            b.setTextSize(5f);
        }
    }
    public void stopGame(String s){

    }
}

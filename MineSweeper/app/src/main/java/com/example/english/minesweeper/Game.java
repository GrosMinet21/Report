package com.example.english.minesweeper;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

public class Game extends Activity
{
    public static final String KEY_DIFFICULTY = "com.example.english.minesweeper.difficulty";
    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_MEDIUM = 1;
    public static final int DIFFICULTY_HARD = 2;
    Layout minefield = Layout(R.id.activity_main);
    Tile[][] tiles;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        int diff = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_EASY);
    }

    public void showGameBoard(int diff)
    {
        int easyRows = 10;
        int easyColumns = 10;
        int easyMines = 20;

        int mediumRows = 15;
        int mediumColumns = 15;
        int mediumMines = 30;

        int hardRows = 20;
        int hardColumns = 20;
        int hardMines = 40;

        int totalRows = easyRows;
        int totalCols = easyColumns;
        int totalMines = easyMines;

        int tileWH = 10;
        int tilePadding = 10;

        switch(diff)
        {
            case 0:
                break;
            case 1:
                totalRows = mediumRows;
                totalCols = mediumColumns;
                totalMines = mediumMines;
                break;
            case 2:
                totalRows = hardRows;
                totalCols = hardColumns;
                totalMines = hardMines;
                break;
        }

        tiles = new Tile[totalRows][totalCols];
        for(int row=0;row<totalRows;row++)
        {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new LayoutParams(
                    (tileWH * tilePadding) * totalCols, tileWH * tilePadding));

            for(int col=0;col<totalCols;col++)
            {
                tiles[row][col] = new Tile(this);
                tiles[row][col].setDefaults();
                tiles[row][col].setLayoutParams(new LayoutParams
                        (tileWH * tilePadding,  tileWH * tilePadding));
                tiles[row][col].setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
                tableRow.addView(tiles[row][col]);
            }
            //add the row to the minefield layout
            mineField.addView(tableRow,new TableLayout.LayoutParams(
                    (tileWH * tilePadding) * totalCols, tileWH * tilePadding));
        }
    }
}


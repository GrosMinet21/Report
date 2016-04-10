package com.example.english.mine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {
    int[][] minefield;
    Button[][] buttonfield;
    GridLayout grid_l;
    int i;
    int j;
    int m = 80;
    boolean intero = true;
    int[] color = {Color.DKGRAY, Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN, Color.CYAN, Color.BLACK, Color.DKGRAY, Color.MAGENTA};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GridLayout grid_l = (GridLayout) findViewById(R.id.grid_button);
        Log.d("lol", grid_l.toString());
        final Button[][] buttonfield = new Button[10][10];
        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttonfield[i][j] = (Button) grid_l.getChildAt(i*10+j);
            }
        }
        Button restart = (Button)findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGame(grid_l, buttonfield);
                m = 80;
            }
        });
        Button check = (Button)findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intero = !intero;
            }
        });
        setGame(grid_l, buttonfield);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setGame(GridLayout grid_l, Button[][] button){
        m = 80;
        TextView txt = (TextView) findViewById(R.id.mines_count);
        txt.setText("Tiles remaining : "+m);
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
        for( i = 0; i<10;i++) {
            for ( j = 0; j < 10; j++) {
                setDefaults(buttonfield[i][j]);
                buttonfield[i][j].setOnClickListener(new View.OnClickListener() {
                    int x = i;
                    int y = j;
                    @Override
                    public void onClick(View v) {
                        if(intero)
                            click(buttonfield[x][y], x, y);
                        else
                            longClick(buttonfield[x][y], x, y);
                    }
                });
            }
        }
    }

    public void setDefaults(Button b){
        b.setText("");
        b.setTextSize(10f);
        b.setBackgroundColor(Color.BLACK);
        b.setEnabled(true);
    }

    public void click(Button b, int i, int j){
        if(minefield[i][j]==1)
            stopGame("");
        else{
            b.setBackgroundColor(Color.WHITE);
            int mines_around = 0;
            if(i==0 && j==0){
                mines_around = minefield[1][0] + minefield[1][1] + minefield[0][1];
            }else if(i==0 && j ==9){
                mines_around = minefield[0][8] + minefield[1][8] + minefield[1][9];
            }else if(i==9 && j==0){
                mines_around = minefield[8][0] + minefield[8][1] + minefield[9][1];
            }else if(i==9 && j== 9){
                mines_around = minefield[9][8] + minefield[8][8] + minefield[8][9];
            }else if(i==0){
                mines_around = minefield[0][j-1] + minefield[0][j+1] + minefield[1][j-1] + minefield[1][j] + minefield[1][j+1];
            }else if(i==9){
                mines_around = minefield[9][j-1] + minefield[9][j+1] + minefield[8][j-1] + minefield[8][j] + minefield[8][j+1];
            }else if(j==0){
                mines_around = minefield[i-1][0] + minefield[i+1][0] + minefield[i-1][1] + minefield[i+1][1] + minefield[i][1];
            }else if(j==9){
                mines_around = minefield[i-1][9] + minefield[i+1][9] + minefield[i-1][8] + minefield[i+1][8] + minefield[i][8];
            }else{
                mines_around = minefield[i-1][j-1] + minefield[i-1][j] + minefield[i-1][j+1] + minefield[i][j-1] + minefield[i][j+1] + minefield[i+1][j-1] + minefield[i+1][j] + minefield[i+1][j+1];
            }
            b.setText(String.valueOf(mines_around));
            b.setTextSize(10f);
            b.setClickable(false);
            b.setTextColor(color[mines_around]);
            m--;
            TextView txt = (TextView) findViewById(R.id.mines_count);
            txt.setText("Tiles remaining : " + m);
            if(m==0)
                stopGame("WIN");
        }
    }

    public void longClick(Button b, int x, int y){
        b.setText("?");
        b.setTextSize(10f);
        b.setTextColor(Color.WHITE);
    }

    public void stopGame(String s){
        if(s=="WIN"){
            AlertDialog builder = new AlertDialog.Builder(this)
                    .setTitle("You won !")
                    .setMessage("Well played, you can try another game !")
                    .setNeutralButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setGame(grid_l, buttonfield);
                                    m = 80;
                                }
                            }).show();
        }
        else{
            AlertDialog builder = new AlertDialog.Builder(this)
                    .setTitle("You lost !")
                    .setMessage("Sorry, you're bad, try again :)")
                    .setNeutralButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setGame(grid_l, buttonfield);
                                    m = 80;
                                }
                            }).show();
        }
    }
}

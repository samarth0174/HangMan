package com.samar.firstlook.hangman;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    //the words
    private String[] words;
    //private ArrayList<String>words;
    //hints arry
    private String[] hints;
    //random for word selection
    private Random rand;
    //store the current word
    private String currWord;
    //the layout holding the answer
    private LinearLayout wordLayout;
    //text views for each letter in the answer
    private TextView[] charViews;
    //letter button grid
    private GridView letters;
    //letter button adapter
    private LetterAdapter ltrAdapt;
    //body part images
    private ImageView[] bodyParts;
    //total parts
    private int numParts=6;
    //current part
    private int currPart;
    //num chars in word
    private int numChars;
    //num correct so far
    private int numCorr;
    //help
    private AlertDialog helpAlert;
    private AlertDialog.Builder hintdialog;
    private AlertDialog hintAlert;
    private Button hintbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor( "#FF8800")));
        getSupportActionBar().hide();


        Resources res = getResources();

    words = res.getStringArray(R.array.tech);

    hints = res.getStringArray(R.array.hinttech);


        rand = new Random();
        currWord = "";

        wordLayout = (LinearLayout) findViewById(R.id.word);
        letters = (GridView) findViewById(R.id.letters);
        hintbutton=(Button)findViewById(R.id.hintbutton);





        bodyParts = new ImageView[numParts];
        bodyParts[0] = (ImageView) findViewById(R.id.head);
        bodyParts[1] = (ImageView) findViewById(R.id.body);
        bodyParts[2] = (ImageView) findViewById(R.id.arm1);
        bodyParts[3] = (ImageView) findViewById(R.id.arm2);
        bodyParts[4] = (ImageView) findViewById(R.id.leg1);
        bodyParts[5] = (ImageView) findViewById(R.id.leg2);


        //set home as up
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        playGame();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_help:
                showHelp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void playGame() {
        //play a new game

        String newWord = words[rand.nextInt(words.length)];
        while (newWord.equals(currWord)) newWord =words[rand.nextInt(words.length)];

        currWord = newWord;
      int index= Arrays.asList(words).indexOf(currWord);
      final String gethint=hints[index];


 //  hint button
    hintbutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            hintdialog=new AlertDialog.Builder(GameActivity.this);
            hintdialog.setTitle("HINT");
            hintdialog.setMessage("\n"+gethint);

            hintdialog.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            hintAlert.dismiss();
                        }});
            hintAlert = hintdialog.create();
            hintdialog.show();

//            hintbutton.setEnabled(false);
//            hintbutton.setTextColor(Color.WHITE);



        }
    });


        charViews = new TextView[currWord.length()];
        wordLayout.removeAllViews();

        //loop through characters

        for(int c=0; c<currWord.length(); c++) {
                charViews[c] = new TextView(this);
                //set the current letter

                charViews[c].setText("" + currWord.charAt(c));
                //set layout
                charViews[c].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                charViews[c].setGravity(Gravity.CENTER);
                charViews[c].setTextColor(Color.WHITE);
                charViews[c].setBackgroundResource(R.drawable.letter_bg);
                //add to display
                wordLayout.addView(charViews[c]);
            }



        //reset adapter
        ltrAdapt=new LetterAdapter(this);
        letters.setAdapter(ltrAdapt);

        //start part at zero
        currPart=0;
        //set word length and correct choices
        numChars=currWord.length();
        numCorr=0;

        //hide all parts
        for(int p=0; p<numParts; p++){
            bodyParts[p].setVisibility(View.INVISIBLE);
        }

    }



    //user has pressed a letter to guess
    public void letterPressed(View view) {

        String ltr = ((TextView) view).getText().toString();
        char letterChar = ltr.charAt(0);

        //disable the letter button and update the background drawable to show the player that the letter has already been played.
        view.setEnabled(false);
        view.setBackgroundResource(R.drawable.letter_down);

        boolean correct = false;
        for (int k = 0; k < currWord.length(); k++) {
            if (currWord.charAt(k) == letterChar) {
                correct = true;
                numCorr++;
                charViews[k].setTextColor(Color.BLACK);
            }
        }
        //check if case won
        if(correct){
            if(numCorr==numChars){
                //disable all buttons
                disableBtns();
                //let user know they have won, ask if they want to play again
                AlertDialog.Builder winBuild = new AlertDialog.Builder(this);
                winBuild.setTitle("YAY,YOU JUST SAVED HIM");
                winBuild.setMessage("You Guessed it Right!\n\nThe answer was:\n\n"+currWord);
                winBuild.setPositiveButton("Play Again",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                GameActivity.this.playGame();
                            }});
                winBuild.setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                GameActivity.this.finish();
                            }});
                winBuild.show();
            }
        }
        //check if user still has guesses
        else if(currPart<numParts){
            //show next part
            bodyParts[currPart].setVisibility(View.VISIBLE);
            currPart++;
        }

        else{
            //user has lost
            disableBtns();
            //let the user know they lost, ask if they want to play again
            AlertDialog.Builder loseBuild = new AlertDialog.Builder(this);
            loseBuild.setTitle("THE MAN IS EXECUTED!");
            loseBuild.setMessage("You lose!\n\nThe answer was:\n\n"+currWord);
            loseBuild.setPositiveButton("Play Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            GameActivity.this.playGame();
                        }});
            loseBuild.setNegativeButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            GameActivity.this.finish();
                        }});
            loseBuild.show();
        }

    }

    //disable letter buttons
    public void disableBtns(){
        int numLetters = letters.getChildCount();
        for(int l=0; l<numLetters; l++){
            letters.getChildAt(l).setEnabled(false);
        }
    }

    //show help information
    public void showHelp(){
        AlertDialog.Builder helpBuild = new AlertDialog.Builder(this);
        helpBuild.setTitle("Help");
        helpBuild.setMessage("Guess the word by selecting the letters.\n\n"
                + "Only 6 wrong selections allowed then the man is Hanged to death!\n\n"+
        "Don't want him hanged,so guess it right!!");
        helpBuild.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        helpAlert.dismiss();
                    }});
        helpAlert = helpBuild.create();
        helpBuild.show();
    }



//    public boolean onKeyDown(int keycode, KeyEvent event) {
//        if (keycode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(true);
//        }
//        return super.onKeyDown(keycode, event);
//    }


}

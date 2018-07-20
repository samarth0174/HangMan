package com.samar.firstlook.hangman;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity{
        //implements AdapterView.OnItemSelectedListener {

    //public static  int choice;
   //  private Spinner spinner;
    private static final String[]paths={"Tech","Movies"};
    Button playBtn;
    Button howto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF000000")));


        Window window = MainActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(Color.parseColor("#FF000000"));


        playBtn=(Button)findViewById(R.id.playBtn);
    playBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            playbtnclicked();
        }
    });

    howto=(Button)findViewById(R.id.howbutton);
    howto.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
                howbuttonclicked();
        }
    });

//
//        spinner = (Spinner)findViewById(R.id.spinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
//                android.R.layout.simple_spinner_item,paths);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
//


    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case (R.id.playBtn): {
//                Intent playIntent = new Intent(this, GameActivity.class);
//                this.startActivity(playIntent);
//                break;
//            }
//            case (R.id.howbutton):
//            {
//                Intent howtoIntent = new Intent(getApplicationContext(), HowToActivity.class);
//                this.startActivity(howtoIntent);
//                break;
//        }
//
//        }
//    }


        public void howbuttonclicked()
                {
                Intent howtoIntent = new Intent(getApplicationContext(), HowToActivity.class);
                this.startActivity(howtoIntent);

        }

         public void   playbtnclicked()
         {
             Intent playIntent = new Intent(this, GameActivity.class);
                this.startActivity(playIntent);

         }



}

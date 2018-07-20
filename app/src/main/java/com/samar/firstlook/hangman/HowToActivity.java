package com.samar.firstlook.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class HowToActivity extends AppCompatActivity implements OnClickListener {

    TextView howtohead;
    TextView rules;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);


      getSupportActionBar().hide();


        howtohead=(TextView)findViewById(R.id.howto);
        rules=(TextView)findViewById(R.id.details);

        start=(Button)findViewById(R.id.startgamebutton);
        start.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.startgamebutton)
        {
            Intent startintent=new Intent(this,GameActivity.class);
            startActivity(startintent);
        }
    }
}

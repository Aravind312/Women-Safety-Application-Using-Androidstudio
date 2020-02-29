package com.example.myapplication8;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main48Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main48);
        SharedPreferences sp1=this.getSharedPreferences("Login",MODE_PRIVATE);
        String em=sp1.getString("email",null);
        TextView textView = (TextView) findViewById(R.id.prof_email);
        textView.setText(em);


    }
}

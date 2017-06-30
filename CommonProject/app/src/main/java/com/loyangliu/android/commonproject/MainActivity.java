package com.loyangliu.android.commonproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loyangliu.android.commonproject.R;

public class MainActivity extends AppCompatActivity {

    public static int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt3 = (Button)this.findViewById(R.id.button3);
        Button bt4 = (Button)this.findViewById(R.id.button4);

        // for normal button test
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                times++;

                TextView textview = (TextView)MainActivity.this.findViewById(R.id.mytext);
                textview.setText("click time " + times);
                //Toast.makeText(MainActivity.this, "click button3", Toast.LENGTH_SHORT).show();
            }
        });

        // for crash test
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a;
                a = 1/0;
                Toast.makeText(MainActivity.this, "click button4", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

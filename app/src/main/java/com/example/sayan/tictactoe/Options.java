package com.example.sayan.tictactoe;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.sayan.tictactoe.fragment_Example.SignInFragment;
import com.google.firebase.auth.FirebaseAuth;

public class Options extends AppCompatActivity {

    Button btnEasy,btnHard,btnOnline;
    FragmentManager fm;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        btnEasy=findViewById(R.id.easy);
        btnHard=findViewById(R.id.hard);
        btnOnline=findViewById(R.id.onlineButton);

        fm=getSupportFragmentManager();

        firebaseAuth=FirebaseAuth.getInstance();

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Options.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Options.this, Hard.class);
                startActivity(intent);
            }
        });

        btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseAuth.getCurrentUser()!=null)
                {
                    Intent intent=new Intent(Options.this,FriendsActivity.class);
                    startActivity(intent);
                    return;
                }

                SignInFragment sFragment=new SignInFragment();
                sFragment.show(fm,"Sign In Page");
            }
        });
    }
}

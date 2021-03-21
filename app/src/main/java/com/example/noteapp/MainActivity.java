package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noteapp.R;

public class MainActivity extends AppCompatActivity {
    Context context;
    SharedPreferences prefs;

    EditText txtUsername, txtPassword;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                if(!prefs.getString("username", "##########").equals(username)) {
                    Toast.makeText(context, "Invalid username!", Toast.LENGTH_SHORT).show();
                } else if(!prefs.getString("password", "##########").equals(password)) {
                    Toast.makeText(context, "Wrong password!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, NotesActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!prefs.getString("username", "").equals("")) {
            btnRegister.setVisibility(View.GONE);
        } else {
            txtUsername.setEnabled(true);
            txtPassword.setEnabled(true);
        }
    }
}
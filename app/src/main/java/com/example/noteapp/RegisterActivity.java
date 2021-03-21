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

public class RegisterActivity extends AppCompatActivity {
    EditText txtUsername, txtPassword;
    Button btnRegister;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                if(username.equals("") || password.equals("")){
                    Toast.makeText(context, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                } else if(password.length()<8) {
                    Toast.makeText(context, "Password too short!", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();

                    Toast.makeText(context, "Registration Complete!", Toast.LENGTH_SHORT).show();

                    onBackPressed();
                }
            }
        });
    }
}
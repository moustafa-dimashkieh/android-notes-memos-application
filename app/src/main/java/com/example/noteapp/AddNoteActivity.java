package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {
    DatabaseHandler dbh;
    Context context;
    int action;

    TextView lblHeader;
    EditText txtTitle, txtContent;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        context = this;
        dbh = new DatabaseHandler(context);

        lblHeader = findViewById(R.id.lblHeader);
        txtTitle = findViewById(R.id.txtTitle);
        txtContent = findViewById(R.id.txtContent);
        btnSave = findViewById(R.id.btnSave);

        action = getIntent().getIntExtra("action", -1);
        String title, content;
        switch(action){
            case 0:
                title = getIntent().getStringExtra("title");
                content = getIntent().getStringExtra("content");

                btnSave.setVisibility(View.GONE);
                txtTitle.setEnabled(false);
                txtContent.setEnabled(false);

                lblHeader.setText("View Note");
                txtTitle.setText(title);
                txtContent.setText(content);
                break;
            case 1:
                title = getIntent().getStringExtra("title");
                content = getIntent().getStringExtra("content");

                lblHeader.setText("Edit Note");
                txtTitle.setText(title);
                txtContent.setText(content);
                break;
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = txtTitle.getText().toString();
                String content = txtContent.getText().toString();
                switch(action){
                    case 1:
                        if (title.equals("") || content.equals("")) {
                            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        } else {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                            Date now = Calendar.getInstance().getTime();
                            String date = formatter.format(now);

                            int ID = getIntent().getIntExtra("ID", -1);

                            dbh.editMemo(ID, title, date, content);

                            Intent intent = new Intent(context, NotesActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        break;

                    case 2:
                        if (title.equals("") || content.equals("")) {
                            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        } else {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                            Date now = Calendar.getInstance().getTime();
                            String date = formatter.format(now);

                            Memo memo = new Memo();
                            memo.setTitle(title);
                            memo.setContent(content);
                            memo.setDate(date);

                            dbh.addMemo(memo);

                            Intent intent = new Intent(context, NotesActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        break;
                }

            }
        });
    }
}
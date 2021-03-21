package com.example.noteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.noteapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotesActivity extends AppCompatActivity {
    DatabaseHandler dbh;
    Context context;
    Memo[] memos;

    ListView list;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        context = this;

        list = findViewById(R.id.lvNotes);
        btnAdd = findViewById(R.id.btnAdd);

        dbh = new DatabaseHandler(this);
        memos = dbh.getMemos();

        String[] titles = new String[memos.length];
        String[] dates = new String[memos.length];
        String[] previews = new String[memos.length];
        for(int i=0; i< memos.length; i++) {
            Log.d("TAG", memos[i].toString());
            titles[i] = memos[i].getTitle();
            dates[i] = memos[i].getDate();
            previews[i] = memos[i].getContent();
        }

        MyListAdapter adapter = new MyListAdapter(this, titles, dates, previews);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, AddNoteActivity.class);
                intent.putExtra("action", 0);
                intent.putExtra("ID", memos[position].getID());
                intent.putExtra("title", memos[position].getTitle());
                intent.putExtra("content", memos[position].getContent());
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setTitle("Select A Item ");
                String[] menuItems = {"Edit", "Delete"};

                alertBuilder.setItems(menuItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                Intent intent = new Intent(context, AddNoteActivity.class);
                                intent.putExtra("action", 1);
                                intent.putExtra("ID", memos[position].getID());
                                intent.putExtra("title", memos[position].getTitle());
                                intent.putExtra("content", memos[position].getContent());
                                startActivity(intent);
                                break;

                            case 1:
                                dbh.deleteMemo(memos[position].getID());
                                Intent intent1 = getIntent();
                                startActivity(intent1);
                                finish();
                                break;
                        }
                    }
                });

                AlertDialog dialog = alertBuilder.create();
                dialog.show();
                return true;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddNoteActivity.class);
                intent.putExtra("action", 2);
                startActivity(intent);
            }
        });
    }
}
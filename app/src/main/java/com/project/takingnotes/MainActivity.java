package com.project.takingnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    NoteAdapter adapter;
    RecyclerView recyclerView;
    DBHandler dbHandler;
    FloatingActionButton button;
    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.noteList);

        dbHandler = new DBHandler(this);
        notes = dbHandler.readNote();

        adapter = new NoteAdapter(notes, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WriteActivity.class);
            startActivity(intent);
        });

//        registerForContextMenu(recyclerView);
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        menu.add("Edit");
//        menu.add("Delete");
//    }

//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        int id = info.position;

//        if (item.getTitle().equals("Edit"))
//        {
//            Intent intent = new Intent(MainActivity.this, WriteActivity.class);
//
//            intent.putExtra("id", notes.get(id).getId());
//            intent.putExtra("title", notes.get(id).getTitle());
//            intent.putExtra("content", notes.get(id).getContent());
//            intent.putExtra("date", notes.get(id).getDate());
//
//            startActivity(intent);
//            return true;
//        }
//
//        if (item.getTitle().equals("Delete")) {
//            dbHandler.deleteNote(notes.get(id).getId());
//            adapter.notifyDataSetChanged();
//            Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        return super.onContextItemSelected(item);
//    }
}
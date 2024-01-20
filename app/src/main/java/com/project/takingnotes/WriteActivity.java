package com.project.takingnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WriteActivity extends AppCompatActivity {
    DBHandler dbHandler;
    String id = null;
    EditText title, content;
    TextView date;
    ImageView btnBack, btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        title = findViewById(R.id.editTitle);
        content = findViewById(R.id.editContent);
        date = findViewById(R.id.editDate);
        date.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        btnBack = findViewById(R.id.btnBack);
        btnConfirm = findViewById(R.id.btnConfirm);
        dbHandler = new DBHandler(getApplicationContext());



        Intent i = getIntent();
        if (i != null) {
            id = i.getStringExtra("id");
            title.setText(i.getStringExtra("title"));
            date.setText(i.getStringExtra("date"));
            content.setText(i.getStringExtra("content"));
        }

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(WriteActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnConfirm.setOnClickListener(v -> {
            if (id == null) {
                if(!title.getText().toString().isEmpty() && !content.getText().toString().isEmpty()) {
                    dbHandler.addNote(title.getText().toString(), content.getText().toString());
                    Toast.makeText(this, "Add successfully", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                dbHandler.updateNote(id, title.getText().toString(), content.getText().toString());
                Toast.makeText(this, "Update successfully", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(WriteActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
package com.hamonusers.booklibrary;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SavedBooksActivity extends AppCompatActivity {

    private ArrayList<BookInfo> savedBooks;
    private BookDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_books);

        RecyclerView recyclerView = findViewById(R.id.rv2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new BookDbHelper(this);
        savedBooks = dbHelper.getAllBooks();

        BookAdapter adapter = new BookAdapter(savedBooks, this, true);
        recyclerView.setAdapter(adapter);

    }
}

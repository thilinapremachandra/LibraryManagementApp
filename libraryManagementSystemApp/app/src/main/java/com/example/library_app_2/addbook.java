package com.example.library_app_2;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class addbook extends AppCompatActivity {


    private EditText bookName;
    private EditText bookAuthor;
    private EditText bookPublication;
    private EditText bookQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);

        EdgeToEdge.enable(this);

        // Initialize views
        bookName = findViewById(R.id.txtbname);
        bookAuthor = findViewById(R.id.txtbauthor);
        bookPublication = findViewById(R.id.txtbpublication);
        bookQuantity = findViewById(R.id.txtquntity);


        Button addBook = findViewById(R.id.btnadd);


        addBook.setOnClickListener(v -> {
            String name = bookName.getText().toString().trim();
            String author = bookAuthor.getText().toString().trim();
            String publication = bookPublication.getText().toString().trim();
            String quantityStr = bookQuantity.getText().toString().trim();

            if (name.isEmpty() || author.isEmpty() || publication.isEmpty() || quantityStr.isEmpty()) {
                Toast.makeText(addbook.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = Integer.parseInt(quantityStr);

            try (DbHandler dbHelper = new DbHandler(addbook.this)) {
                dbHelper.insertBookDetails(name, author, publication, quantity);
                Toast.makeText(addbook.this, "Book added successfully!", Toast.LENGTH_SHORT).show();
                bookName.setText("");
                bookAuthor.setText("");
                bookPublication.setText("");
                bookQuantity.setText("");

            } catch (Exception e) {
                Log.e("AddBookActivity", "Error occurred while adding book", e);
                Toast.makeText(addbook.this, "Error adding book", Toast.LENGTH_SHORT).show();
            }
        });




        // Adjust layout when system bars change
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


}
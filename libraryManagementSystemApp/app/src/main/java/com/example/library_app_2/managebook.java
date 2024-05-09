package com.example.library_app_2;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class managebook extends AppCompatActivity {

    private String bookName;
    private String author, publisher, quantity;
    private EditText txtTitle;
    private EditText txtauthor;
    private EditText txtpublisher;
    private EditText txtquantity;
    private TextView bookId;
    private DbHandler dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managebook);


        txtTitle = findViewById(R.id.txtupdatebookname);
        txtauthor = findViewById(R.id.txtupdatebookauthor);
        txtpublisher = findViewById(R.id.txtupdatepublication);
        txtquantity = findViewById(R.id.txtupdatequantity);
        bookId = findViewById(R.id.txtbookid);
        Button btnUpdate = findViewById(R.id.btnupdate);
        Button btnDelete = findViewById(R.id.btndelete);

        dbHelper = new DbHandler(this);

        retrieveExtrasFromIntent();

        retrieveDataFromDatabase();

        // Set onClickListeners for the buttons
        btnUpdate.setOnClickListener(v -> updateBook());
        btnDelete.setOnClickListener(v -> deleteBook());
    }

    private void retrieveDataFromDatabase() {
        Cursor cursor = dbHelper.getBookByName(bookName);
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DbHandler.BOOK_ID);
            int authorIndex = cursor.getColumnIndex(DbHandler.BOOK_AUTHOR);
            int publicationIndex = cursor.getColumnIndex(DbHandler.BOOK_PUBLICATION);
            int quantityIndex = cursor.getColumnIndex(DbHandler.BOOK_QUANTITY);

            bookId.setText(cursor.getString(idIndex));
            author = cursor.getString(authorIndex);
            publisher = cursor.getString(publicationIndex);
            quantity = cursor.getString(quantityIndex);
            cursor.close();
        }
        txtTitle.setText(bookName);
        txtauthor.setText(author);
        txtpublisher.setText(publisher);
        txtquantity.setText(quantity);
    }

    private void retrieveExtrasFromIntent() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bookName = extras.getString("bookName");
        }
    }

    private void updateBook() {
        String name = txtTitle.getText().toString().trim();
        String author = txtauthor.getText().toString().trim();
        String publication = txtpublisher.getText().toString().trim();
        String quantityStr = txtquantity.getText().toString().trim();

        if (name.isEmpty() || author.isEmpty() || publication.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(managebook.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = Integer.parseInt(quantityStr);

        int bookIdInt = Integer.parseInt(bookId.getText().toString());

        try (DbHandler dbHelper = new DbHandler(managebook.this)) {
            dbHelper.updateBookDetails(bookIdInt, name, author, publication, quantity);
            Toast.makeText(managebook.this, "Book updated successfully!", Toast.LENGTH_SHORT).show();
            bookId.setText("");
            txtTitle.setText("");
            txtauthor.setText("");
            txtpublisher.setText("");
            txtquantity.setText("");

        } catch (Exception e) {
            Log.e("UpdateBookActivity", "Error occurred while updating book", e);
            Toast.makeText(managebook.this, "Error updating book", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteBook() {
        int bookIdInt = Integer.parseInt(bookId.getText().toString());

        try (DbHandler dbHelper = new DbHandler(managebook.this)) {
            dbHelper.deleteBook(bookIdInt);
            Toast.makeText(managebook.this, "Book deleted successfully!", Toast.LENGTH_SHORT).show();
            // Clear the input fields after deletion
            bookId.setText("");
            txtTitle.setText("");
            txtauthor.setText("");
            txtpublisher.setText("");
            txtquantity.setText("");
        } catch (Exception e) {
            Log.e("AddBookActivity", "Error occurred while deleting book", e);
            Toast.makeText(managebook.this, "Error deleting book", Toast.LENGTH_SHORT).show();
        }
    }
}

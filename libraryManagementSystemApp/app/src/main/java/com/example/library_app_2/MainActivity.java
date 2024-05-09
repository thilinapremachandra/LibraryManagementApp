package com.example.library_app_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button add, view, update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.btnaddbookhome);
        view = findViewById(R.id.btnviewbookhome);
        update = findViewById(R.id.btnupdatebookhome);

        add.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, addbook.class);
            startActivity(intent);
        });
        view.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, viewbook.class);
            startActivity(intent);

        });
        update.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, viewbook.class);
            startActivity(intent);

        });

}
}
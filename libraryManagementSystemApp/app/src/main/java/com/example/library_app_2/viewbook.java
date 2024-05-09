package com.example.library_app_2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class viewbook extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<CardItem> cardItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewbook);

        recyclerView = findViewById(R.id.recyclerViewviewbook);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        cardItemList = new ArrayList<>();
        adapter = new MyAdapter(this, cardItemList);
        recyclerView.setAdapter(adapter);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    } @Override
    protected void onResume() {
        super.onResume();
        refreshAdapters();
    }

    private void refreshAdapters() {
        cardItemList.clear();

        DbHandler dbHelper = new DbHandler(this);
        Cursor cursor = dbHelper.getAllBook();

        if (cursor != null && cursor.moveToFirst()) {
            int bookNameIndex = cursor.getColumnIndex(DbHandler.BOOK_NAME);
            int bookAuthorIndex  = cursor.getColumnIndex(DbHandler.BOOK_AUTHOR);
            int bookPublisherIndex  = cursor.getColumnIndex(DbHandler.BOOK_PUBLICATION);
            int bookQuantityIndex  = cursor.getColumnIndex(DbHandler.BOOK_QUANTITY);



            do {
                String name = cursor.getString(bookNameIndex);
                String author = cursor.getString(bookAuthorIndex);
                String publisher = cursor.getString(bookPublisherIndex);
                String quantity = cursor.getString(bookQuantityIndex);

                cardItemList.add(new CardItem(name, author, publisher, quantity));
            } while (cursor.moveToNext());
            cursor.close();
        }
        dbHelper.close();

        adapter.notifyDataSetChanged();
    }

    private static class CardItem {
        String name;
        String author;
        String publisher;
        String quantity;



        CardItem(String name, String author, String publisher, String quantity ) {
            this.name = name;
            this.author = author;
            this.publisher = publisher;
            this.quantity = quantity;

        }
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private final List<CardItem> cardItems;
        private final Context context;

        MyAdapter(Context context, List<CardItem> cardItems) {
            this.context = context;
            this.cardItems = cardItems;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            CardItem item = cardItems.get(position);

            holder.textViewName.setText(item.name);
            holder.textViewAuthor.setText(item.author);
            holder.textViewPublisher.setText(item.publisher);
            holder.textViewQuantity.setText(item.quantity);



            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, managebook.class);
                intent.putExtra("bookName", item.name);
                context.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return cardItems.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            final TextView textViewName;
            final TextView textViewAuthor;
            final TextView textViewPublisher;
            final TextView textViewQuantity;


            ViewHolder(View itemView) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.txtallbookcardtitle);
                textViewAuthor = itemView.findViewById(R.id.txtallbookcardauthor);
                textViewPublisher = itemView.findViewById(R.id.txtallbookcardpublisher);
                textViewQuantity = itemView.findViewById(R.id.txtallbookcardquantity);

            }
        }
    }
}
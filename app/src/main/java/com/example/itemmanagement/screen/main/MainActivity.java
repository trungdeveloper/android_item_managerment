package com.example.itemmanagement.screen.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.itemmanagement.R;
import com.example.itemmanagement.screen.add.AddItemActivity;
import com.example.itemmanagement.adapter.MyAdapter;
import com.example.itemmanagement.database.AppDatabase;
import com.example.itemmanagement.model.Item;
import com.example.itemmanagement.screen.update.UpdateActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        RecyclerView recyclerView = findViewById(R.id.recyclerItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new MyAdapter();


        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddItemActivity.class));
            }
        });




        mAdapter.listener = new MyAdapter.OnItemClickListener() {
            @Override
            public void onUpdateClick(int position) {
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra("id", mAdapter.items.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                deleteItemFromDatabase(position);
            }
        };
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllItemsFromDatabase();
    }

    @SuppressLint("StaticFieldLeak")
    private void getAllItemsFromDatabase(){
        new AsyncTask<Void, Void, List<Item>>(){

            @Override
            protected List<Item> doInBackground(Void... voids) {
                return db.itemDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Item> items) {
                super.onPostExecute(items);
                mAdapter.items = items;
                mAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void deleteItemFromDatabase(final int id){
        new AsyncTask<Void, Void, List<Item>>(){

            @Override
            protected List<Item> doInBackground(Void... voids) {
                List<Item> items = db.itemDao().getAll();
                db.itemDao().delete(items.get(id));
                return db.itemDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Item> items) {
                super.onPostExecute(items);
                mAdapter.items = items;
                mAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}

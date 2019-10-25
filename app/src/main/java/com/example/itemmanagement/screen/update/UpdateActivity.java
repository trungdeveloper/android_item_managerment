package com.example.itemmanagement.screen.update;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.itemmanagement.R;
import com.example.itemmanagement.database.AppDatabase;
import com.example.itemmanagement.model.Item;

import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    AppDatabase db;
    EditText edtName;
    EditText edtQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        edtName = findViewById(R.id.edit_name);
        edtQuantity = findViewById(R.id.edit_quantity);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");

        getItemFromDatabase(id);
    }

    @SuppressLint("StaticFieldLeak")
    private void getItemFromDatabase(final int id){
        new AsyncTask<Void, Void, List<Item>>(){

            @Override
            protected List<Item> doInBackground(Void... voids) {
                return db.itemDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Item> items) {
                super.onPostExecute(items);
                edtName.setText(""+items.get(id).getName());
                edtQuantity.setText(""+items.get(id).getQuantity());
            }
        }.execute();
    }
}

package com.example.itemmanagement.screen.add;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.itemmanagement.R;
import com.example.itemmanagement.database.AppDatabase;
import com.example.itemmanagement.model.Item;

public class AddItemActivity extends AppCompatActivity {
    AppDatabase db;
    EditText edtName;
    EditText edtQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        edtName = findViewById(R.id.name);
        edtQuantity = findViewById(R.id.quantity);
        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemToDatabase();
                finish();
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private void addItemToDatabase() {
        final String name = edtName.getText().toString();
        final String quantity = edtQuantity.getText().toString();
        if (name.isEmpty() || quantity.isEmpty()) {
            Toast.makeText(this, "All fields must not null", Toast.LENGTH_SHORT).show();
            return;
        }

        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                Item newItem = new Item(name, Integer.parseInt(quantity));
                db.itemDao().insert(newItem);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(AddItemActivity.this, "New item added", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
}

package com.example.itemmanagement.screen.update;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
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
        final int id = bundle.getInt("id");

        Button button =   findViewById(R.id.button_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = new Item(edtName.getText().toString(), Integer.parseInt(edtQuantity.getText().toString()));
                item.setId(id);
                updateItem(item);
            }
        });

        getItemFromDatabase(id);
    }

    @SuppressLint("StaticFieldLeak")
    private void getItemFromDatabase(final int id){
        new AsyncTask<Void, Void, List<Item>>(){

            @Override
            protected List<Item> doInBackground(Void... voids) {
                return db.itemDao().getItem(id);
            }

            @Override
            protected void onPostExecute(List<Item> items) {
                super.onPostExecute(items);
                edtName.setText(""+items.get(0).getName());
                edtQuantity.setText(""+items.get(0).getQuantity());
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void updateItem(final Item item){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                 db.itemDao().update(item);
                 return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                showSuccessDialog();
            }

        }.execute();
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage("Update Success")
                .setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
    }
}

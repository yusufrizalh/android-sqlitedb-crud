package id.inixindosurabaya.androidsqlitedb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // inisialisasi
    EditText edit_id, edit_name, edit_mark;
    Button btn_add, btn_view, btn_update, btn_delete;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mengenali komponen
        helper = new DatabaseHelper(this);
        edit_id = findViewById(R.id.edit_id);
        edit_name = findViewById(R.id.edit_name);
        edit_mark = findViewById(R.id.edit_mark);
        btn_add = findViewById(R.id.btn_add);
        btn_view = findViewById(R.id.btn_view);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);

        // event handling
        btn_add.setOnClickListener(this);
        btn_view.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add){
            // membuat perintah untuk menambah data baru
            insertData();
        } else if (v.getId() == R.id.btn_view) {
            // membuat perintah untuk melihat data
            getAllData();
        } else if (v.getId() == R.id.btn_update) {
            // membuat perintah untuk mengubah data
            updateData();
        } else if (v.getId() == R.id.btn_delete) {
            // membuat perintah untuk menghapus data
            deleteData();
        }
    }

    public void deleteData() {
        Integer deletedRow = helper.deleteData(edit_id.getText().toString());
        // cek jika berhasil menghapus data
        if (deletedRow > 0) {
            Toast.makeText(MainActivity.this,
                    "Data is deleted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this,
                    "Data is not deleted!", Toast.LENGTH_SHORT).show();
        }
        edit_id.setText("");
        edit_id.requestFocus();
    }

    public void updateData() {
        boolean isUpdated = helper.updateData(edit_id.getText().toString(),
                edit_name.getText().toString(),
                edit_mark.getText().toString());
        // cek jika berhasil mengubah data
        if (isUpdated == true) {
            Toast.makeText(MainActivity.this,
                    "Data is updated!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this,
                    "Data is not updated!", Toast.LENGTH_SHORT).show();
        }
        edit_name.setText("");
        edit_mark.setText("");
        edit_name.requestFocus();
    }

    public void getAllData() {
        Cursor cursor = helper.getAllData();
        // cek apakah ada data atau tidak
        if (cursor.getCount() == 0) {   // data kosong
            showMessage("Error", "No data is found!");
            return;
        }

        // jika data ditemukan
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append("ID: " + cursor.getString(0) + "\n");
            buffer.append("Name: " + cursor.getString(1) + "\n");
            buffer.append("Mark: " + cursor.getString(2) + "\n\n");
        }

        // menampilkan semua data secara keseluruhan
        showMessage("Get All Data", buffer.toString());
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton("OK", null);
        builder.setCancelable(true);
        builder.show();

    }

    public void insertData() {
        boolean isInserted = helper.insertData(edit_name.getText().toString(),
                edit_mark.getText().toString());
        // cek jika berhasil menambah data
        if (isInserted == true) {
            Toast.makeText(MainActivity.this,
                    "Data is inserted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this,
                    "Data is not inserted!", Toast.LENGTH_SHORT).show();
        }
        edit_name.setText("");
        edit_mark.setText("");
        edit_name.requestFocus();
    }
}

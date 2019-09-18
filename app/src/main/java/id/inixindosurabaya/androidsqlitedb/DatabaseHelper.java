package id.inixindosurabaya.androidsqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    // inisialisasi kebutuhan database
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    private static final int DATABASE_VERSION = 1;

    // membuat table fields
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "MARK";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // membuat struktur tabel dalam database
        db.execSQL("CREATE TABLE student_table(id integer primary key autoincrement, " +
                "name text, " +
                "mark integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // membuat perintah untuk menambah data baru (Create)
    public boolean insertData(String name, String mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, name);
        values.put(COL_3, mark);
        long result = db.insert(TABLE_NAME, null, values);

        // cek jika menambah data gagal
        if (result == -1) {
            return false;
        } else {    // jika berhasil
            return true;
        }
    }

    // membuat perintah untuk menampilkan semua data (Read)
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM student_table", null);
        return cursor;
    }

    // membuat perintah untuk mengubah data (Update)
    public boolean updateData(String id, String name, String mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, id);
        values.put(COL_2, name);
        values.put(COL_3, mark);
        db.update(TABLE_NAME, values, "ID = ?", new String[] {id});
        return true;
    }

    // membuat perintah untuk menghapus data (Delete)
    public int deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
}

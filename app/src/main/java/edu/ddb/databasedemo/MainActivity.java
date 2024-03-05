package edu.ddb.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseHelper helper;
    SQLiteDatabase db;
    public static final String TAG = "MainActivity";
    Button btnInsert;
    Button btnGetItems;
    Button btnDelete;
    Button btnUpdate;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DatabaseHelper(this,
                                    "items.db",
                                    null,
                                    helper.DATABASE_VERSION);

        db = helper.getWritableDatabase();

        btnGetItems = findViewById(R.id.btnGetItems);
        btnGetItems.setOnClickListener(this);

        btnInsert = findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(this);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        tvInfo = findViewById(R.id.tvInfo);

    }

    public void onClick(View view)
    {
        int buttonId = view.getId();

        if(buttonId == R.id.btnGetItems)
        {
            GetItems();
        }
        else if (buttonId == R.id.btnInsert)
        {
            Insert();
        }
        else if (buttonId == R.id.btnUpdate)
        {
            Update();
        }
        else
        {
            Delete();
        }
    }

    private void GetItems() {
        if(db != null){
            Cursor cursor = db.rawQuery("select * from tblItem;", null);
            String msg = "";
            while(cursor.moveToNext())
            {
                int id = cursor.getInt(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                Log.d(TAG, "GetItems: " + id + ":" + firstName + ":" + lastName);
                msg += id + ") " + lastName + "\r\n";
            }
            cursor.close();
            tvInfo.setText(msg);
        }
    }
    private void Insert() {
        try{
            if(db != null)
            {
                ContentValues contentValues = new ContentValues();
                contentValues.put("FirstName", "Dean");
                contentValues.put("LastName", "Bartel");
                db.insert("tblItem", null, contentValues);

                GetItems();
            }
        }
        catch(Exception e)
        {
            Log.d(TAG, "Insert: " + e.getMessage());
        }
    }
    private void Update() {
        try{
            if(db != null)
            {
                String whereClause = "Id == 2";
                ContentValues contentValues = new ContentValues();
                contentValues.put("LastName", "Simpson");
                db.update("tblItem", contentValues, whereClause, null);
                GetItems();
            }
        }
        catch(Exception e)
        {
            Log.d(TAG, "Insert: " + e.getMessage());
        }
    }

    private void Delete() {
        try{
            if(db != null)
            {
                db.execSQL("delete from tblItem");
                // or
                //db.delete("tblItem", null, null);
                GetItems();
            }
        }
        catch(Exception e)
        {
            Log.d(TAG, "Insert: " + e.getMessage());
        }
    }


}
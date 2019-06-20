package com.example.creditmanagement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import static java.lang.Integer.parseInt;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    public HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table contacts " +
                        "(id integer primary key, name text,phone text,email text, street text,place text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertContact (String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.insert("contacts", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  res = db.rawQuery( "select * from contacts where id="+id+"",null  );
    return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public boolean deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
         db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    return true;

    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();
        ArrayList<String> array_list2 = new ArrayList<String>();
        ArrayList<String> array_list3 = new ArrayList<String>();
        ArrayList<String> array_list4 = new ArrayList<String>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res4 =  db.rawQuery( "select * from contacts", null );
        res4.moveToFirst();

        while(res4.isAfterLast() == false){
            array_list.add(res4.getString(res4.getColumnIndex(CONTACTS_COLUMN_NAME)));
            array_list2.add(res4.getString(res4.getColumnIndex(CONTACTS_COLUMN_ID)));
            array_list4.add(res4.getString(res4.getColumnIndex(CONTACTS_COLUMN_CITY)));

            res4.moveToNext();
        }

        int i = array_list.size() -1 ;
        while(i != -1)
        { array_list3.add( array_list2.get(i)+ ","+ array_list.get(i) + "           Credits : "+array_list4.get(i));
           i --;
        }
        return (array_list3);
    }

    public boolean transferCredits(int to , int from , int value)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Log.e("onClick1",""+to);
        Log.e("onClick2",""+from);
        Log.e("onClick3",""+value);


        Cursor res4 =  db.rawQuery( "select place from contacts where id ="+to, null );
        res4.moveToFirst();


           int i = parseInt(res4.getString(res4.getColumnIndex(CONTACTS_COLUMN_CITY)));

        Log.e("onClick4",""+i);

        Cursor res6 =  db.rawQuery( "select place from contacts where id ="+from, null );
        res6.moveToFirst();


        int j = parseInt(res6.getString(res6.getColumnIndex(CONTACTS_COLUMN_CITY)));



        contentValues.put("place" ,i+value);

        ContentValues contentValues2 = new ContentValues();
        if(j-value < 0) {
            return false;

        }





        contentValues2.put("place" ,j-value);

        db.update("contacts",contentValues2 , "id = ? ", new String[] { Integer.toString(from) } );

        db.update("contacts",contentValues , "id = ? ", new String[] { Integer.toString(to) } );
    return true;
    }

}

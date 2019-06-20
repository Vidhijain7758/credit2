package com.example.creditmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Activity3 extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    public ListView obj;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        mydb = new DBHelper(this);
        ArrayList array_list = mydb.getAllCotacts();


        ArrayAdapter arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list );

        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                Log.e("onClick",""+arg0);
                Log.e("onClick",""+arg1);
                Log.e("onClick",""+arg2);
                Log.e("onClick",""+arg3);
                String s = (String)arg0.getItemAtPosition(arg2);
                Log.e("onClick",""+s);
                String cont[] = s.split(",");


               int idTo = parseInt(cont[0]);

                int id_To_Search = idTo ;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(), Activity2.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }
}


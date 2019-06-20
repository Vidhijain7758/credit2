package com.example.creditmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Transfer extends AppCompatActivity {


    public final static String EXTRA_MESSAGE = "MESSAGE";
    public ListView obj;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        mydb = new DBHelper(this);
        ArrayList array_list = mydb.getAllCotacts();


        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);

        obj = (ListView) findViewById(R.id.listView2);
        obj.setAdapter(arrayAdapter2);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Credit Amount :");


        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                Toast.makeText(getApplicationContext(), "Select Contact to Transfer",
                        Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.create();

        builder.show();
        mydb = new DBHelper(this);


        final String[] m_Text = {""};
        final int[] id_To_Update = {0};



        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                String s = (String) arg0.getItemAtPosition(arg2);
                Log.e("onClick", "" + s);
                String cont[] = s.split(",");


                int idTo = parseInt(cont[0]);

                int id_To_Search = idTo;
                m_Text[0] = (input.getText().toString());
                id_To_Update[0] = Integer.parseInt(m_Text[0]);
                int Value = id_To_Update[0];

                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    int i = extras.getInt("id");


                    boolean done = mydb.transferCredits(id_To_Search, i, Value);



                    if (done == false) {

                        Log.e("onClick5",""+done);
                        Toast.makeText(getApplicationContext(), "INVALID AMOUNT : NOT ENOUGH CREDITS AVAILABLE",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);




                    }
                    else {

                        Toast.makeText(getApplicationContext(), "tranferred Successfully",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);


                    }
                }

            }

        });




    }

}



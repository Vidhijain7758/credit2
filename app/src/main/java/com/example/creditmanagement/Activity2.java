package com.example.creditmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydba;


    TextView name;
    TextView phone;
    TextView email;
    TextView street;
    TextView place;
    int id_To_Update2 = 0;  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);


        name = (TextView) findViewById(R.id.editTextName);
        phone = (TextView) findViewById(R.id.editTextPhone);
        email = (TextView) findViewById(R.id.editTextStreet);
        street = (TextView) findViewById(R.id.editTextEmail);
        place = (TextView) findViewById(R.id.editTextCity);

        mydba = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = mydba.getData(Value);
                id_To_Update2 = Value;
                Log.e("onClick",""+id_To_Update2);
                String ar[] = rs.getColumnNames();
                Log.e("onClick",""+ar[0]+ar[1]);
                rs.moveToFirst();
                if (rs.getCount() >0 )
                {

                    int a = rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME);
                    String nam2;
                    nam2 = rs.getString(a);
                    String phon = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
                    String emai = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
                    String stree = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_STREET));
                    String plac = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));

                    if (!rs.isClosed()) {
                        rs.close();
                    }
                    Button b = (Button) findViewById(R.id.button1);
                    b.setVisibility(View.VISIBLE);
                    b.setText("Transfer Credits");
                    b.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Bundle dataBundle = new Bundle();
                                                 dataBundle.putInt("id", id_To_Update2);











                                                 Intent in = new Intent(getApplicationContext() , Transfer.class);

                                                 in.putExtras(dataBundle);
                                                 startActivity(in);




                                             }


                                         });
                    name.setText((CharSequence) nam2);
                    name.setFocusable(false);
                    name.setClickable(false);

                    phone.setText((CharSequence) phon);
                    phone.setFocusable(false);
                    phone.setClickable(false);

                    email.setText((CharSequence) emai);
                    email.setFocusable(false);
                    email.setClickable(false);

                    street.setText((CharSequence) stree);
                    street.setFocusable(false);
                    street.setClickable(false);

                    place.setText((CharSequence) plac);
                    place.setFocusable(false);
                    place.setClickable(false);


                }
                else {Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_SHORT).show();

                }
        }



        }
    }
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            Bundle extras = getIntent().getExtras();

            if (extras != null) {
                    getMenuInflater().inflate(R.menu.main_menu , menu);
                }

            return true;
        }

        public void run (View view){
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                int Value = extras.getInt("id");
                if (Value > 0) {
                    if (mydba.updateContact(id_To_Update2, name.getText().toString(),
                            phone.getText().toString(), email.getText().toString(),
                            street.getText().toString(), place.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Activity3.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (mydba.insertContact(name.getText().toString(), phone.getText().toString(),
                            email.getText().toString(), street.getText().toString(),
                            place.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "done",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "not done",
                                Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(), Activity3.class);
                    startActivity(intent);
                }
            }
        }

    }

package com.example.mycontactapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editAddress, editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_Name);
        editAddress = findViewById(R.id.editText_Address);
        editPhone = findViewById(R.id.editText_Phone);

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: instantiated DatabaseHelper");
    }

    public void addData(View view) {
        boolean isInserted = myDb.insertData(editName.getText().toString(), editAddress.getText().toString(), editPhone.getText().toString());

        if (isInserted == true) {
            Toast.makeText(MainActivity.this, "Success - contact inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Failed - contact not inserted", Toast.LENGTH_LONG).show();
        }
    }

    public void viewData(View view) {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            showMessage("Error", "No data found in database");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            //append res column 0, ... is the buffer = see Stringbuffer and Cursor api's
            buffer.append("ID: " + res.getString(0) + "\n");
            buffer.append("Name: " + res.getString(1) + "\n");
            buffer.append("Phone: " + res.getString(2) + "\n");
            buffer.append("Contact: " + res.getString(3) + "\n");
        }

        showMessage("Data", buffer.toString());
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public static final String EXTRA_MESSAGE = "com.example.mycontactapp.MESSAGE";

    public void searchData(View view) {
        Cursor res = myDb.getAllData();

        if (res.getCount() == 0) {
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            if (!editName.getText().toString().equals("") && editPhone.getText().toString().equals("") && editAddress.getText().toString().equals("")) {// name ONLY
                if (res.getString(1).equals(editName.getText().toString())) {
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Phone Number: " + res.getString(2) + "\n");
                    buffer.append("Address: " + res.getString(3) + "\n");
                }
            }
            else if (editName.getText().toString().equals("") && !editPhone.getText().toString().equals("") && editAddress.getText().toString().equals("")){//number ONLY
                if (res.getString(2).equals(editPhone.getText().toString())) {
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Phone Number: " + res.getString(2) + "\n");
                    buffer.append("Address: " + res.getString(3) + "\n");
                }
            }
            else if (editName.getText().toString().equals("") && editPhone.getText().toString().equals("") && !editAddress.getText().toString().equals("")){ // address ONLY
                if (res.getString(3).equals(editAddress.getText().toString())) {
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Phone Number: " + res.getString(2) + "\n");
                    buffer.append("Address: " + res.getString(3) + "\n");
                }
            }
            else if (!editName.getText().toString().equals("") && !editPhone.getText().toString().equals("") && editAddress.getText().toString().equals("")){//name and number ONLY
                if (res.getString(1).equals(editName.getText().toString()) && res.getString(2).equals(editPhone.getText().toString())) {
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Phone Number: " + res.getString(2) + "\n");
                    buffer.append("Address: " + res.getString(3) + "\n");
                }
            }
            else if (!editName.getText().toString().equals("") && editPhone.getText().toString().equals("") && !editAddress.getText().toString().equals("")){//name and address ONLY
                if (res.getString(1).equals(editAddress.getText().toString()) && res.getString(3).equals(editAddress.getText().toString())){
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Phone Number: " + res.getString(2) + "\n");
                    buffer.append("Address: " + res.getString(3) + "\n");
                }
            }
            else if (editName.getText().toString().equals("") && !editPhone.getText().toString().equals("") && !editAddress.getText().toString().equals("")){//number and address ONLY
                if (res.getString(2).equals(editPhone.getText().toString()) && res.getString(3).equals(editAddress.getText().toString())){
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Phone Number: " + res.getString(2) + "\n");
                    buffer.append("Address: " + res.getString(3) + "\n");
                }
            }
            else {
                if (res.getString(1).equals(editName.getText().toString()) &&
                        res.getString(2).equals(editPhone.getText().toString()) &&
                        res.getString(3).equals(editAddress.getText().toString())) {
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Phone Number: " + res.getString(2) + "\n");
                    buffer.append("Address: " + res.getString(3) + "\n");
                }
            }
        }

        showMessage("Data", buffer.toString());
    }




    public String otherRecords()
    {
        Cursor res = myDb.getAllData();
        Log.d("MyContactApp", "MainActiviyt: other records received cursor");
        StringBuffer buff = new StringBuffer();
        int contacts = 0;

        while (res.moveToNext())
        {
            if (res.getString(1).equals(editName.getText().toString())){
                for (int i=1; i<4; i++)
                {
                    buff.append(res.getColumnName(i) + ": " + res.getString(i) + "\n");

                }
                buff.append("\n");
                contacts++;
            }
        }

        if (contacts == 0)
        {
            return "No contact was found";
        }
        else
            return buff.toString();
    }
}


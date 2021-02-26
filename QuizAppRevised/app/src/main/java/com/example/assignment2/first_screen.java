package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

//Icons made by <a href="https://icon54.com/" title="Pixel perfect">Pixel perfect</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>

public class first_screen extends AppCompatActivity {


    //declare all components to be interacted with
    private EditText editTextName;
    private Button btnName;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        //connect all components to xml counterparts
        editTextName = (EditText) findViewById(R.id.editTextName);
        btnName = (Button) findViewById(R.id.btnName);

        //create an arrayList to hold available quiz names
        ArrayList<String> quizList = new ArrayList<String>();
        //WIP HERE
        //collect all valid files from folder, give user choice and then bundle response w/ intent
        //https://stackoverflow.com/questions/25828121/get-file-list-in-android-raw-resource-directory-from-code
        //retrieve fields from raw file
        Field[] fields = R.raw.class.getFields();
        //add filenames to arraylist
        for(int i = 0; i < fields.length; i++)
        {
            quizList.add(fields[i].getName());
        }


        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user name and set to User object
                String userName = editTextName.getText().toString();
                if(userName.isEmpty()){
                    String warningString = "Please enter a name!";
                    Toast warningToast = Toast.makeText(first_screen.this, warningString, Toast.LENGTH_SHORT);
                    warningToast.show();
                }
                else {
                    //bundle and move to next view goes here
                    Intent i = new Intent("quiz_screen");
                    Bundle extras = new Bundle();
                    extras.putString("KEY", userName);
                    i.putExtras(extras);
                    startActivityForResult(i, 1);
                    Intent j = new Intent(first_screen.this, quiz_screen.class);
                }

            }
        });

    }






}
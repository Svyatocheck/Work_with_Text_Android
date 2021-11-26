package com.example.workwithtext;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.workwithtext.Scripts.User;
import com.example.workwithtext.Scripts.csvWorker;
import com.example.workwithtext.Scripts.jsonWorker;
import com.example.workwithtext.Scripts.txtWorker;
import com.example.workwithtext.Scripts.xmlWorker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName;
    Button btnSave;
    Button loadBtn;
    Spinner langList;
    Spinner ideList;
    ConstraintLayout constraint;
    CheckBox maleCheck;
    CheckBox femaleCheck;
    String position = "";
    CheckBox csvCheck;
    CheckBox txtCheck;
    CheckBox jsonCheck;
    CheckBox xmlCheck;
    TextView outputTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraint = (ConstraintLayout) findViewById(R.id.constraint);

        btnSave = (Button)findViewById(R.id.btnSave);
        loadBtn =(Button)findViewById(R.id.btnLoad);

        etName = (EditText) findViewById(R.id.etName);
        maleCheck = (CheckBox)findViewById(R.id.maleCheck);
        femaleCheck =(CheckBox)findViewById(R.id.femaleCheck);
        ideList = (Spinner) findViewById(R.id.ideList);
        langList = (Spinner) findViewById(R.id.langList);

        txtCheck = (CheckBox)findViewById(R.id.txtBox);
        csvCheck = (CheckBox) findViewById(R.id.csvCheck);
        jsonCheck = (CheckBox) findViewById(R.id.jsonCheck);
        xmlCheck = (CheckBox)findViewById(R.id.xmlCheck);

        outputTxt = (TextView)findViewById(R.id.textView);
        btnSave.setOnClickListener(this);
        etName.setOnClickListener(v -> etName.getText().clear());

        String[] arraySpinner = new String[] {
                "Click to select IDE", "Android Studio", "IntelliJ IDEA", "VS Code", "Visual Studio 2019", "Eclipse", "SublimeText",
        };
        Spinner ideList = (Spinner) findViewById(R.id.ideList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ideList.setAdapter(adapter);

        String[] arraySecondSpinner = new String[] {
                "Click to select Language", "Python", "C++", "Java", "Basic", "Kotlin", "C#",
        };
        Spinner langList = (Spinner) findViewById(R.id.langList);
        ArrayAdapter<String> secAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySecondSpinner);
        secAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langList.setAdapter(secAdapter);
    }

    public void chekers(View v) {
        try {
            if (maleCheck.isChecked() && femaleCheck.isChecked()) {
                femaleCheck.toggle();
                maleCheck.toggle();
            }

        } catch (Exception ignore)
        {}
    }


    public void onLoadClick(View v)
    {
        if (txtCheck.isChecked())
        {
            outputTxt.setText(txtWorker.readFromTxtFile(this));
        }

        if (csvCheck.isChecked())
        {
            outputTxt.setText(csvWorker.readFromCsv(this));
        }

        if (jsonCheck.isChecked())
        {
            outputTxt.setText(jsonWorker.readJson(this));
        }

        if (xmlCheck.isChecked()){
            outputTxt.setText(xmlWorker.ParseXML(this));
        }
    }

    @Override
    public void onClick(View v) {

        String name = etName.getText().toString();
        String ide = ideList.getSelectedItem().toString();
        String lang = langList.getSelectedItem().toString();
        String sex = "";

        if (maleCheck.isChecked()) {
            sex = "Male";
        }

        if (femaleCheck.isChecked()) {
            sex = "Female";
        }

        // txt save
        String data = name + "\n" + sex + "\n" + ide + "\n" + lang + "\n";
        txtWorker.txtWrite(data, this);

        // csv save
        data = name + ";" + sex + ";" + ide + ";" + lang + ";";
        csvWorker.WriteToCsv(data, this);

        //json save
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Name", name);
            jsonObject.put("Sex", sex);
            jsonObject.put("IDE", ide);
            jsonObject.put("Lang", lang);
            jsonWorker.writeJson(jsonObject, this);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // xml write
        User person = new User(name, sex, ide, lang);
        try {
            xmlWorker.CreateXMLString(person, this);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
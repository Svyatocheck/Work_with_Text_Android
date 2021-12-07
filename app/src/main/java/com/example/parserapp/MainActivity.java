package com.example.parserapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<User> adapter;
    private EditText nameText, ageText;
    private List<User> users;
    ListView listView;
    Spinner langList;
    Spinner ideList;
    ConstraintLayout constraint;
    CheckBox maleCheck;
    CheckBox femaleCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XmlPullParser xpp = getResources().getXml(R.xml.users);
        UserResourceParserXML parser = new UserResourceParserXML();
        if(parser.parse(xpp))
        {
            for(UserXML prod: parser.getUsers()){
                Log.d("XML", prod.toString());
            }
        }
        nameText = findViewById(R.id.nameText);
        langList = findViewById(R.id.langList);
        ideList = findViewById(R.id.ideList);
        maleCheck = findViewById(R.id.maleCheck);
        femaleCheck = findViewById(R.id.femaleCheck);

        //ageText = findViewById(R.id.ageText);
        listView = findViewById(R.id.list);

        users = new ArrayList<>();

        nameText.setOnClickListener(v -> nameText.getText().clear());

        String[] arraySpinner = new String[] {
                "Click to select IDE", "Android Studio", "IntelliJ IDEA", "VS Code", "Visual Studio 2019", "Eclipse", "SublimeText",
        };
        Spinner ideList = (Spinner) findViewById(R.id.ideList);
        ArrayAdapter<String> adapter_1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ideList.setAdapter(adapter_1);

        String[] arraySecondSpinner = new String[] {
                "Click to select Language", "Python", "C++", "Java", "Basic", "Kotlin", "C#",
        };
        Spinner langList = (Spinner) findViewById(R.id.langList);
        ArrayAdapter<String> secAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySecondSpinner);
        secAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langList.setAdapter(secAdapter);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        listView.setAdapter(adapter);


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

    public void addUser(View view){
        String name = nameText.getText().toString();
        String ide = ideList.getSelectedItem().toString();
        String lang = langList.getSelectedItem().toString();
        String sex = "";
        if (maleCheck.isChecked()) {
            sex = "Male";
        }

        if (femaleCheck.isChecked()) {
            sex = "Female";
        }

        User user = new User(name, ide, lang, sex);
        users.add(user);
        adapter.notifyDataSetChanged();
    }

    public void save(View view){

        boolean result = JSONHelper.exportToJSON(this, users);
        if(result){
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }
    }
    public void open(View view){
        users = JSONHelper.importFromJSON(this);
        if (users!=null){
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
            listView.setAdapter(adapter);
            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
        }
    }
}


package com.example.yychiu.todolistapp;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class CreateActivity extends AppCompatActivity {
    MyDBHelper myDBHelper;
    Button button;
    EditText todo;
    DatePicker picker;
    public static final boolean BOOLEAN = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        myDBHelper = new MyDBHelper(this);
        InitView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = todo.getText().toString();
                String cdate = picker.getYear()+"-"+(picker.getMonth()+1)+"-"+picker.getDayOfMonth();
                ContentValues values = new ContentValues();
                values.put("info",info);
                values.put("cdate",cdate);
                values.put("done",BOOLEAN);
                myDBHelper.getWritableDatabase().insert("exp",null,values);

                startActivity(new Intent(CreateActivity.this,MainActivity.class));
            }
        });

    }

    private void InitView() {
        button = findViewById(R.id.action);
        todo = findViewById(R.id.todoET);
        picker = findViewById(R.id.datepicker);
    }
}

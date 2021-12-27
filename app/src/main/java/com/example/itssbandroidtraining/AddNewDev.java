package com.example.itssbandroidtraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNewDev extends AppCompatActivity {

    EditText name,desg;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_dev);
        name=findViewById(R.id.edtDevName);
        desg=findViewById(R.id.edtDevDesg);
        btnAdd=findViewById(R.id.btnAddDev);
    }

    public void add(View view) {
     //   MainActivity.Developer developer=new MainActivity.Developer(name.getText().toString(),desg.getText().toString());
        if(btnAdd.getText().toString()=="Save Developer"){
            Intent intent=new Intent();
            intent.putExtra("devname",name.getText().toString());
            intent.putExtra("devdesg",desg.getText().toString());
            setResult(RESULT_OK,intent);
            finish();
        }

    }
}
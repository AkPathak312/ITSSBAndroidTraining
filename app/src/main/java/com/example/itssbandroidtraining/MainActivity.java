package com.example.itssbandroidtraining;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    boolean isLoggedIn;
    Spinner spinnerFrom,spinnerTo;
    EditText edtDate;
    String[] spinner={"AUH","CAI","BAH","DOA"};
    TextView txtSelected;
    TextView btnLogin;
    ArrayList<Developer> developers=new ArrayList<>();
    CustomListAdapter customListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        developers.add(new Developer("Puru Verma","Developer"));
        developers.add(new Developer("Vinay Kumar","UI/UX"));
        developers.add(new Developer("Sachinand","Database"));
        developers.add(new Developer("Rasitha","HTML/JS"));

        btnLogin=findViewById(R.id.btnLogout);
        checkLoginStatus();
        if(isLoggedIn==false){
            btnLogin.setText("Login");
        }else{
            btnLogin.setText("Logout");
        }
        spinnerFrom=findViewById(R.id.spinnerFrom);
        spinnerTo=findViewById(R.id.spinnerTo);
        edtDate=findViewById(R.id.edtDate);
        txtSelected=findViewById(R.id.txtSelected);
        listView=findViewById(R.id.listView);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,spinner);
        spinnerTo.setAdapter(arrayAdapter);
        spinnerFrom.setAdapter(arrayAdapter);

        customListAdapter=new CustomListAdapter(this,developers);
        listView.setAdapter(customListAdapter);
        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem =spinnerFrom.getSelectedItem().toString();
                txtSelected.setText("Selected Item : "+selectedItem);
                Log.d("onItemSelected : ",selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btnLogin.setText("Logout");
                if(isLoggedIn==true){
                    btnLogin.setText("Login");
                    isLoggedIn=false;
                }
                else{
                    btnLogin.setText("Logout");
                    isLoggedIn=true;
                    saveToSharedPreference(isLoggedIn);
                }
            }
        });
    }

    private void checkLoginStatus() {
        SharedPreferences sharedPreferences=getSharedPreferences("loginNode",MODE_PRIVATE);
        isLoggedIn= sharedPreferences.getBoolean("isLoggedIn",false);
    }

    private void saveToSharedPreference(boolean isLoggedIn) {
        SharedPreferences sharedPreferences=getSharedPreferences("loginNode",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("isLoggedIn",isLoggedIn);
        editor.commit();
    }

    public void onDateClick(View view) {
        Calendar calendar=Calendar.getInstance();
        int year,month,date;
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        date=calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edtDate.setText(dayOfMonth+"/"+month+"/"+year);
            }
        },year,month,date);
        datePickerDialog.show();
    }

    public void openAddNewDev(View view) {
        Intent i=new Intent(MainActivity.this,AddNewDev.class);
      //  startActivity(i);
        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            String name= data.getStringExtra("devname");
            String desg=data.getStringExtra("devdesg");
            Developer newDev=new Developer(name,desg);
            developers.add(newDev);
            customListAdapter.notifyDataSetChanged();
        }
    }


    public static class Developer {
        String name;
        String designation;
        public Developer(String name, String designation) {
            this.name = name;
            this.designation = designation;
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            spinnerFrom.setVisibility(View.GONE);
            spinnerTo.setVisibility(View.GONE);
            edtDate.setVisibility(View.GONE);
        }
        if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            spinnerFrom.setVisibility(View.VISIBLE);
            spinnerTo.setVisibility(View.VISIBLE);
            edtDate.setVisibility(View.VISIBLE);
        }
    }
}